<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">
        </a-row>
      </a-form>
    </div>
    <!-- 查询区域-END -->

    <!-- 操作按钮区域 -->
    <div class="table-operator">
      <a-button @click="handleAdd" type="primary" icon="plus">新增</a-button>
      <a-button type="primary" icon="download" @click="handleExportXls('wms_app_versions')">导出</a-button>
      <a-upload name="file" :showUploadList="false" :multiple="false" :headers="tokenHeader" :action="importExcelUrl" @change="handleImportExcel">
        <a-button type="primary" icon="import">导入</a-button>
      </a-upload>
      <!-- 高级查询区域 -->
      <j-super-query :fieldList="superFieldList" ref="superQueryModal" @handleSuperQuery="handleSuperQuery"></j-super-query>
      <a-dropdown v-if="selectedRowKeys.length > 0">
        <a-menu slot="overlay">
          <a-menu-item key="1" @click="batchDel"><a-icon type="delete"/>删除</a-menu-item>
        </a-menu>
        <a-button style="margin-left: 8px"> 批量操作 <a-icon type="down" /></a-button>
      </a-dropdown>
    </div>

    <!-- table区域-begin -->
    <div>
      <div class="ant-alert ant-alert-info" style="margin-bottom: 16px;">
        <i class="anticon anticon-info-circle ant-alert-icon"></i> 已选择 <a style="font-weight: 600">{{ selectedRowKeys.length }}</a>项
        <a style="margin-left: 24px" @click="onClearSelected">清空</a>
      </div>

      <a-table
        ref="table"
        size="middle"
        :scroll="{x:true}"
        bordered
        rowKey="id"
        :columns="columns"
        :dataSource="dataSource"
        :pagination="ipagination"
        :loading="loading"
        :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange}"
        class="j-table-force-nowrap"
        @change="handleTableChange">

        <template slot="htmlSlot" slot-scope="text">
          <div v-html="text"></div>
        </template>
        <template slot="imgSlot" slot-scope="text">
          <span v-if="!text" style="font-size: 12px;font-style: italic;">无图片</span>
          <img v-else :src="getImgView(text)" height="25px" alt="" style="max-width:80px;font-size: 12px;font-style: italic;"/>
        </template>
        <template slot="fileSlot" slot-scope="text">
          <span v-if="!text" style="font-size: 12px;font-style: italic;">无文件</span>
          <a-button
            v-else
            :ghost="true"
            type="primary"
            icon="download"
            size="small"
            @click="downloadFile(text)">
            下载
          </a-button>
        </template>

        <span slot="action" slot-scope="text, record">
          <a @click="handleEdit(record)">编辑</a>

          <a-divider type="vertical" />
          <a-dropdown>
            <a class="ant-dropdown-link">更多 <a-icon type="down" /></a>
            <a-menu slot="overlay">
              <a-menu-item>
                <a @click="handleDetail(record)">详情</a>
              </a-menu-item>
              <a-menu-item>
                <a-popconfirm title="确定删除吗?" @confirm="() => handleDelete(record.id)">
                  <a>删除</a>
                </a-popconfirm>
              </a-menu-item>
            </a-menu>
          </a-dropdown>
        </span>

      </a-table>
    </div>

    <wms-app-versions-modal ref="modalForm" @ok="modalFormOk"></wms-app-versions-modal>
  </a-card>
</template>

<script>

  import '@/assets/less/TableExpand.less'
  import { mixinDevice } from '@/utils/mixin'
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import WmsAppVersionsModal from './modules/WmsAppVersionsModal'

  export default {
    name: 'WmsAppVersionsList',
    mixins:[JeecgListMixin, mixinDevice],
    components: {
      WmsAppVersionsModal
    },
    data () {
      return {
        description: 'wms_app_versions管理页面',
        // 表头
        columns: [
          {
            title: '#',
            dataIndex: '',
            key:'rowIndex',
            width:60,
            align:"center",
            customRender:function (t,r,index) {
              return parseInt(index)+1;
            }
          },
          {
            title:'逻辑删除标记',
            align:"center",
            dataIndex: 'delFlag'
          },
          {
            title:'版本号',
            align:"center",
            dataIndex: 'code'
          },
          {
            title:'包名',
            align:"center",
            dataIndex: 'name'
          },
          {
            title:'下载地址',
            align:"center",
            dataIndex: 'location'
          },
          {
            title:'接单司机电话',
            align:"center",
            dataIndex: 'consignorIphone'
          },
          {
            title:'中转费用',
            align:"center",
            dataIndex: 'transferMoney'
          },
          {
            title:'是否强制更新 0 否 1是',
            align:"center",
            dataIndex: 'sstatus'
          },
          {
            title:'更新说明',
            align:"center",
            dataIndex: 'remark'
          },
          {
            title:'是否发布',
            align:"center",
            dataIndex: 'issueIs'
          },
          {
            title: '操作',
            dataIndex: 'action',
            align:"center",
            fixed:"right",
            width:147,
            scopedSlots: { customRender: 'action' }
          }
        ],
        url: {
          list: "/wms/wmsAppVersions/list",
          delete: "/wms/wmsAppVersions/delete",
          deleteBatch: "/wms/wmsAppVersions/deleteBatch",
          exportXlsUrl: "/wms/wmsAppVersions/exportXls",
          importExcelUrl: "wms/wmsAppVersions/importExcel",
          
        },
        dictOptions:{},
        superFieldList:[],
      }
    },
    created() {
    this.getSuperFieldList();
    },
    computed: {
      importExcelUrl: function(){
        return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`;
      },
    },
    methods: {
      initDictConfig(){
      },
      getSuperFieldList(){
        let fieldList=[];
        fieldList.push({type:'string',value:'delFlag',text:'逻辑删除标记'})
        fieldList.push({type:'string',value:'code',text:'版本号'})
        fieldList.push({type:'string',value:'name',text:'包名'})
        fieldList.push({type:'string',value:'location',text:'下载地址'})
        fieldList.push({type:'string',value:'consignorIphone',text:'接单司机电话'})
        fieldList.push({type:'number',value:'transferMoney',text:'中转费用'})
        fieldList.push({type:'string',value:'sstatus',text:'是否强制更新 0 否 1是'})
        fieldList.push({type:'string',value:'remark',text:'更新说明'})
        fieldList.push({type:'string',value:'issueIs',text:'是否发布'})
        this.superFieldList = fieldList
      }
    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less';
</style>
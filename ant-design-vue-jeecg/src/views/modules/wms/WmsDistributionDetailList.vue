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
      <a-button type="primary" icon="download" @click="handleExportXls('配送单明细表')">导出</a-button>
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

    <wms-distribution-detail-modal ref="modalForm" @ok="modalFormOk"></wms-distribution-detail-modal>
  </a-card>
</template>

<script>

  import '@/assets/less/TableExpand.less'
  import { mixinDevice } from '@/utils/mixin'
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import WmsDistributionDetailModal from './modules/WmsDistributionDetailModal'

  export default {
    name: 'WmsDistributionDetailList',
    mixins:[JeecgListMixin, mixinDevice],
    components: {
      WmsDistributionDetailModal
    },
    data () {
      return {
        description: '配送单明细表管理页面',
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
            title:'单号',
            align:"center",
            dataIndex: 'code'
          },
          {
            title:'单据日期',
            align:"center",
            dataIndex: 'billdate',
            customRender:function (text) {
              return !text?"":(text.length>10?text.substr(0,10):text)
            }
          },
          {
            title:'货物名称',
            align:"center",
            dataIndex: 'goodsName'
          },
          {
            title:'配送单Id',
            align:"center",
            dataIndex: 'distributionId'
          },
          {
            title:'包装',
            align:"center",
            dataIndex: 'packaging'
          },
          {
            title:'件数',
            align:"center",
            dataIndex: 'number'
          },
          {
            title:'T/米',
            align:"center",
            dataIndex: 'quantity'
          },
          {
            title:'保价',
            align:"center",
            dataIndex: 'supportValue'
          },
          {
            title:'保费',
            align:"center",
            dataIndex: 'premium'
          },
          {
            title:'运费',
            align:"center",
            dataIndex: 'freight'
          },
          {
            title:'合计',
            align:"center",
            dataIndex: 'totalMoney'
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
          list: "/wms/wmsDistributionDetail/list",
          delete: "/wms/wmsDistributionDetail/delete",
          deleteBatch: "/wms/wmsDistributionDetail/deleteBatch",
          exportXlsUrl: "/wms/wmsDistributionDetail/exportXls",
          importExcelUrl: "wms/wmsDistributionDetail/importExcel",
          
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
        fieldList.push({type:'string',value:'code',text:'单号'})
        fieldList.push({type:'date',value:'billdate',text:'单据日期'})
        fieldList.push({type:'string',value:'goodsName',text:'货物名称'})
        fieldList.push({type:'string',value:'distributionId',text:'配送单Id'})
        fieldList.push({type:'string',value:'packaging',text:'包装'})
        fieldList.push({type:'string',value:'number',text:'件数'})
        fieldList.push({type:'string',value:'quantity',text:'T/米'})
        fieldList.push({type:'string',value:'supportValue',text:'保价'})
        fieldList.push({type:'string',value:'premium',text:'保费'})
        fieldList.push({type:'string',value:'freight',text:'运费'})
        fieldList.push({type:'string',value:'totalMoney',text:'合计'})
        this.superFieldList = fieldList
      }
    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less';
</style>
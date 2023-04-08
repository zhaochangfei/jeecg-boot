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
      <a-button type="primary" icon="download" @click="handleExportXls('wms_car')">导出</a-button>
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

    <wms-car-modal ref="modalForm" @ok="modalFormOk"></wms-car-modal>
  </a-card>
</template>

<script>

  import '@/assets/less/TableExpand.less'
  import { mixinDevice } from '@/utils/mixin'
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import WmsCarModal from './modules/WmsCarModal'

  export default {
    name: 'WmsCarList',
    mixins:[JeecgListMixin, mixinDevice],
    components: {
      WmsCarModal
    },
    data () {
      return {
        description: 'wms_car管理页面',
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
            title:'驾驶员',
            align:"center",
            dataIndex: 'driver'
          },
          {
            title:'电话',
            align:"center",
            dataIndex: 'iphone'
          },
          {
            title:'车牌号',
            align:"center",
            dataIndex: 'licensePlateNumber'
          },
          {
            title:'司机状态',
            align:"center",
            dataIndex: 'sstatus'
          },
          {
            title:'车辆类型',
            align:"center",
            dataIndex: 'carType'
          },
          {
            title:'车牌颜色',
            align:"center",
            dataIndex: 'carColor'
          },
          {
            title:'身份证号码',
            align:"center",
            dataIndex: 'idCard'
          },
          {
            title:'道路运输证号码',
            align:"center",
            dataIndex: 'rsqas'
          },
          {
            title:'驾驶证号',
            align:"center",
            dataIndex: 'drivingLicence'
          },
          {
            title:'车辆状态',
            align:"center",
            dataIndex: 'carStatus'
          },
          {
            title:'开户行',
            align:"center",
            dataIndex: 'openingBank'
          },
          {
            title:'银行卡号',
            align:"center",
            dataIndex: 'bankCard'
          },
          {
            title:'是否绑定GPS',
            align:"center",
            dataIndex: 'gps'
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
          list: "/wms/wmsCar/list",
          delete: "/wms/wmsCar/delete",
          deleteBatch: "/wms/wmsCar/deleteBatch",
          exportXlsUrl: "/wms/wmsCar/exportXls",
          importExcelUrl: "wms/wmsCar/importExcel",
          
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
        fieldList.push({type:'string',value:'driver',text:'驾驶员'})
        fieldList.push({type:'string',value:'iphone',text:'电话'})
        fieldList.push({type:'string',value:'licensePlateNumber',text:'车牌号'})
        fieldList.push({type:'string',value:'sstatus',text:'司机状态'})
        fieldList.push({type:'string',value:'carType',text:'车辆类型'})
        fieldList.push({type:'string',value:'carColor',text:'车牌颜色'})
        fieldList.push({type:'string',value:'idCard',text:'身份证号码'})
        fieldList.push({type:'string',value:'rsqas',text:'道路运输证号码'})
        fieldList.push({type:'string',value:'drivingLicence',text:'驾驶证号'})
        fieldList.push({type:'string',value:'carStatus',text:'车辆状态'})
        fieldList.push({type:'string',value:'openingBank',text:'开户行'})
        fieldList.push({type:'string',value:'bankCard',text:'银行卡号'})
        fieldList.push({type:'string',value:'gps',text:'是否绑定GPS'})
        this.superFieldList = fieldList
      }
    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less';
</style>
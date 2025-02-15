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
      <a-button type="primary" icon="download" @click="handleExportXls('配送单')">导出</a-button>
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

    <wms-distribution-modal ref="modalForm" @ok="modalFormOk"></wms-distribution-modal>
  </a-card>
</template>

<script>

  import '@/assets/less/TableExpand.less'
  import { mixinDevice } from '@/utils/mixin'
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import WmsDistributionModal from './modules/WmsDistributionModal'

  export default {
    name: 'WmsDistributionList',
    mixins:[JeecgListMixin, mixinDevice],
    components: {
      WmsDistributionModal
    },
    data () {
      return {
        description: '配送单管理页面',
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
            title:'起运站',
            align:"center",
            dataIndex: 'originatingStation'
          },
          {
            title:'发货人id',
            align:"center",
            dataIndex: 'consignorId'
          },
          {
            title:'发货人身份证号',
            align:"center",
            dataIndex: 'consignorIdcard'
          },
          {
            title:'发货人电话',
            align:"center",
            dataIndex: 'consignorIphone'
          },
          {
            title:'到达站',
            align:"center",
            dataIndex: 'arrivalStation'
          },
          {
            title:'收货人id',
            align:"center",
            dataIndex: 'consigneeId'
          },
          {
            title:'收货人电话',
            align:"center",
            dataIndex: 'consigneeIphone'
          },
          {
            title:'现付',
            align:"center",
            dataIndex: 'spotPayment'
          },
          {
            title:'预付（代收货款）',
            align:"center",
            dataIndex: 'prepay'
          },
          {
            title:'提付',
            align:"center",
            dataIndex: 'withdrawal'
          },
          {
            title:'总运费（费用合计）',
            align:"center",
            dataIndex: 'sumMoney'
          },
          {
            title:'经办人',
            align:"center",
            dataIndex: 'operator'
          },
          {
            title:'车辆Id',
            align:"center",
            dataIndex: 'carId'
          },
          {
            title:'车辆电话',
            align:"center",
            dataIndex: 'carIphone'
          },
          {
            title:'逻辑删除标记',
            align:"center",
            dataIndex: 'delFlag'
          },
          {
            title:'重量',
            align:"center",
            dataIndex: 'weight'
          },
          {
            title:'件数',
            align:"center",
            dataIndex: 'piece'
          },
          {
            title:'是否中转 0否 1是',
            align:"center",
            dataIndex: 'transferIs'
          },
          {
            title:'中转费',
            align:"center",
            dataIndex: 'transferMoney'
          },
          {
            title:'保费',
            align:"center",
            dataIndex: 'premium'
          },
          {
            title:'倒车费',
            align:"center",
            dataIndex: 'backFare'
          },
          {
            title:'送货费',
            align:"center",
            dataIndex: 'deliveryExpense'
          },
          {
            title:'接货费',
            align:"center",
            dataIndex: 'pickUpCharge'
          },
          {
            title:'其他支出',
            align:"center",
            dataIndex: 'otherExpenses'
          },
          {
            title:'应收合计',
            align:"center",
            dataIndex: 'receivable'
          },
          {
            title:'运输类型',
            align:"center",
            dataIndex: 'type'
          },
          {
            title:'送货方式',
            align:"center",
            dataIndex: 'shippingMethod'
          },
          {
            title:'备注',
            align:"center",
            dataIndex: 'remark'
          },
          {
            title:'开票员',
            align:"center",
            dataIndex: 'drawer'
          },
          {
            title:'业务员',
            align:"center",
            dataIndex: 'salesman'
          },
          {
            title:'关联单号',
            align:"center",
            dataIndex: 'publicCode'
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
          list: "/wms/wmsDistribution/list",
          delete: "/wms/wmsDistribution/delete",
          deleteBatch: "/wms/wmsDistribution/deleteBatch",
          exportXlsUrl: "/wms/wmsDistribution/exportXls",
          importExcelUrl: "wms/wmsDistribution/importExcel",
          
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
        fieldList.push({type:'string',value:'originatingStation',text:'起运站'})
        fieldList.push({type:'string',value:'consignorId',text:'发货人id'})
        fieldList.push({type:'string',value:'consignorIdcard',text:'发货人身份证号'})
        fieldList.push({type:'string',value:'consignorIphone',text:'发货人电话'})
        fieldList.push({type:'string',value:'arrivalStation',text:'到达站'})
        fieldList.push({type:'string',value:'consigneeId',text:'收货人id'})
        fieldList.push({type:'string',value:'consigneeIphone',text:'收货人电话'})
        fieldList.push({type:'number',value:'spotPayment',text:'现付'})
        fieldList.push({type:'number',value:'prepay',text:'预付（代收货款）'})
        fieldList.push({type:'number',value:'withdrawal',text:'提付'})
        fieldList.push({type:'string',value:'sumMoney',text:'总运费（费用合计）'})
        fieldList.push({type:'string',value:'operator',text:'经办人'})
        fieldList.push({type:'string',value:'carId',text:'车辆Id'})
        fieldList.push({type:'string',value:'carIphone',text:'车辆电话'})
        fieldList.push({type:'string',value:'delFlag',text:'逻辑删除标记'})
        fieldList.push({type:'number',value:'weight',text:'重量'})
        fieldList.push({type:'string',value:'piece',text:'件数'})
        fieldList.push({type:'string',value:'transferIs',text:'是否中转 0否 1是'})
        fieldList.push({type:'number',value:'transferMoney',text:'中转费'})
        fieldList.push({type:'number',value:'premium',text:'保费'})
        fieldList.push({type:'number',value:'backFare',text:'倒车费'})
        fieldList.push({type:'number',value:'deliveryExpense',text:'送货费'})
        fieldList.push({type:'number',value:'pickUpCharge',text:'接货费'})
        fieldList.push({type:'number',value:'otherExpenses',text:'其他支出'})
        fieldList.push({type:'number',value:'receivable',text:'应收合计'})
        fieldList.push({type:'string',value:'type',text:'运输类型'})
        fieldList.push({type:'string',value:'shippingMethod',text:'送货方式'})
        fieldList.push({type:'string',value:'remark',text:'备注'})
        fieldList.push({type:'string',value:'drawer',text:'开票员'})
        fieldList.push({type:'string',value:'salesman',text:'业务员'})
        fieldList.push({type:'string',value:'publicCode',text:'关联单号'})
        this.superFieldList = fieldList
      }
    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less';
</style>
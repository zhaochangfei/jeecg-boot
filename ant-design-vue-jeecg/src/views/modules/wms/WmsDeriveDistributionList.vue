<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">
          <a-col :xl="5" :lg="5" :md="6" :sm="24">
            <a-form-item label="车牌号">
              <a-input placeholder="请输入车牌号" v-model="queryParam.inputValue"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="5" :lg="5" :md="6" :sm="24">
            <a-form-item label="司机手机号">
              <a-input placeholder="请输入司机手机号" v-model="queryParam.carIphone"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="8" :lg="11" :md="5" :sm="24">
            <a-form-item label="日期">
              <j-date placeholder="请选择开始日期" class="query-group-cust" v-model="queryParam.startTime"></j-date>
              <span class="query-group-split-cust"></span>
              <j-date placeholder="请选择结束日期" class="query-group-cust" v-model="queryParam.endTime"></j-date>
            </a-form-item>
          </a-col>
          <a-col :xl="8" :lg="9" :md="10" :sm="24">
            <span style="" class="table-page-search-submitButtons">
              <a-button type="primary" @click="searchQuery" icon="search">查询</a-button>
              <a-button type="primary" @click="searchReset" icon="reload" style="margin-left: 8px">重置</a-button>
            </span>
          </a-col>
        </a-row>
      </a-form>
    </div>
    <!-- 查询区域-END -->

    <!-- 操作按钮区域 -->
    <div class="table-operator">
      <!-- <a-button @click="handleAdd" type="primary" icon="plus">新增</a-button>
      <a-button type="primary" icon="download" @click="handleExportXls('配送单')">导出</a-button> -->
      <!-- <a-upload name="file" :showUploadList="false" :multiple="false" :headers="tokenHeader" :action="importExcelUrl" @change="handleImportExcel">
        <a-button type="primary" icon="import">导入</a-button> -->
      <!-- </a-upload> -->
      <!-- 高级查询区域 -->
      <!-- <j-super-query :fieldList="superFieldList" ref="superQueryModal" @handleSuperQuery="handleSuperQuery"></j-super-query>
      <a-dropdown v-if="selectedRowKeys.length > 0">
        <a-menu slot="overlay">
          <a-menu-item key="1" @click="batchDel"><a-icon type="delete"/>删除</a-menu-item>
        </a-menu>
        <a-button style="margin-left: 8px"> 批量操作 <a-icon type="down" /></a-button>
      </a-dropdown> -->
    </div>

    <!-- table区域-begin -->
    <div>
      <!-- <div class="ant-alert ant-alert-info" style="margin-bottom: 16px;">
        <i class="anticon anticon-info-circle ant-alert-icon"></i> 已选择 <a style="font-weight: 600">{{ selectedRowKeys.length }}</a>项
        <a style="margin-left: 24px" @click="onClearSelected">清空</a>
      </div> -->

      <j-vxe-table
        ref="table"
        :scroll="{ x: true }"
        bordered
        rowKey="id"
        row-number
        keep-source
        :columns="columns"
        :dataSource="dataSource"
        :pagination="ipagination"
        :loading="loading"
        @pageChange="handlePageChange"
      >
        <template v-slot:action="props">
          <a @click="showDistribution(props)">查看</a>
        </template>
      </j-vxe-table>
    </div>

    <wms-distribution-modal ref="modalForm" @ok="modalFormOk"></wms-distribution-modal>
    <distribution-pup v-if="distributionPup" ref="distributionPup" @distributionPup="rowBack"></distribution-pup>
  </a-card>
</template>

<script>
import '@/assets/less/TableExpand.less'
import { httpAction, getAction } from '@/api/manage'
import { mixinDevice } from '@/utils/mixin'
import { JeecgListMixin } from '@/mixins/JeecgListMixin'
import WmsDistributionModal from './modules/WmsDistributionModal'
import DistributionPup from './pup/DistributionPup'
import { JVXETypes } from '@/components/jeecg/JVxeTable'

export default {
  name: 'WmsDeriveDistributionList',
  mixins: [JeecgListMixin, mixinDevice],
  components: {
    WmsDistributionModal,
    DistributionPup,
  },
  data() {
    return {
      description: '司机配送单管理页面',
      // 表头
      columns: [
        // {
        //   title: '序号',
        //   dataIndex: '',
        //   key: 'rowIndex',
        //   width: 60,
        //   align: 'center',
        //   customRender: function (t, r, index) {
        //     return parseInt(index) + 1
        //   },
        // },
        // {
        //   title: '单号',
        //   align: 'center',
        //   key: 'code',
        // },
        {
          title: '单据日期',
          align: 'center',
          key: 'billdate',
          customRender: function (text) {
            return !text ? '' : text.length > 10 ? text.substr(0, 10) : text
          },
        },
        {
          title: '车辆',
          align: 'center',
          key: 'carId_dictText',
        },
        // {
        //   title: '状态',
        //   align: 'center',
        //   key: 'sstatus_dictText',
        // },
        {
          title: '起运站',
          align: 'center',
          key: 'originatingStation',
        },
        // {
        //   title: '发货人',
        //   align: 'center',
        //   key: 'consignorId_dictText',
        // },
        // {
        //   title: '到达站',
        //   align: 'center',
        //   key: 'arrivalStation',
        // },
        // {
        //   title: '收货人',
        //   align: 'center',
        //   key: 'consigneeId_dictText',
        // },
        {
          title: '总运费（元）',
          align: 'center',
          key: 'sumMoney',
          statistics: ['sum'],
        },
        {
          title: '件数',
          align: 'center',
          key: 'piece',
          statistics: ['sum'],
        },
        {
          title: '重量（kg）',
          align: 'center',
          key: 'weight',
          statistics: ['sum'],
        },
        // {
        //   title: '运输类型',
        //   align: 'center',
        //   dataIndex: 'type_dictText',
        // },
        // {
        //   title: '送货方式',
        //   align: 'center',
        //   dataIndex: 'shippingMethod_dictText',
        // },
        // {
        //   title: '备注',
        //   align: 'center',
        //   dataIndex: 'remark',
        // },
        {
          title: '操作',
          key: 'action',
          width: '100px',
          // 固定在右侧
          fixed: 'right',
          // 对齐方式为居中
          align: 'center',
          // 组件类型定义为【插槽】
          type: JVXETypes.slot,
          // slot 的名称，对应 v-slot 冒号后面和等号前面的内容
          slotName: 'action',
        },
      ],
      url: {
        list: '/wms/wmsDistribution/deriveList',
        delete: '/wms/wmsDistribution/delete',
        deleteBatch: '/wms/wmsDistribution/deleteBatch',
        exportXlsUrl: '/wms/wmsDistribution/exportXls',
        importExcelUrl: 'wms/wmsDistribution/importExcel',
        changeStatus: 'wms/wmsDistribution/updateStatus',
      },
      dictOptions: {},
      superFieldList: [],
      distributionPup: false,
    }
  },
  created() {
    this.getSuperFieldList()
  },
  computed: {
    importExcelUrl: function () {
      return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`
    },
  },
  methods: {
    //发货
    handleStatus(record) {
      this.loading = true
      const that = this
      getAction(this.url.changeStatus, { id: record.id, status: 1 })
        .then((res) => {
          if (res.success) {
            that.$message.success(res.message)
            that.loadData()
          } else {
            that.$message.warning(res.message)
          }
        })
        .finally(() => {
          that.loading = false
        })
    },
    initDictConfig() {},
    getSuperFieldList() {
      let fieldList = []
      fieldList.push({ type: 'string', value: 'code', text: '单号' })
      fieldList.push({ type: 'date', value: 'billdate', text: '单据日期' })
      fieldList.push({ type: 'string', value: 'originatingStation', text: '起运站' })
      fieldList.push({ type: 'string', value: 'consignorId', text: '发货人id' })
      fieldList.push({ type: 'string', value: 'consignorIdcard', text: '发货人身份证号' })
      fieldList.push({ type: 'string', value: 'consignorIphone', text: '发货人电话' })
      fieldList.push({ type: 'string', value: 'arrivalStation', text: '到达站' })
      fieldList.push({ type: 'string', value: 'consigneeId', text: '收货人id' })
      fieldList.push({ type: 'string', value: 'consigneeIphone', text: '收货人电话' })
      fieldList.push({ type: 'number', value: 'spotPayment', text: '现付' })
      fieldList.push({ type: 'number', value: 'prepay', text: '预付（代收货款）' })
      fieldList.push({ type: 'number', value: 'withdrawal', text: '提付' })
      fieldList.push({ type: 'string', value: 'sumMoney', text: '总运费（费用合计）' })
      fieldList.push({ type: 'string', value: 'operator', text: '经办人' })
      fieldList.push({ type: 'string', value: 'carId', text: '车辆Id' })
      fieldList.push({ type: 'string', value: 'carIphone', text: '车辆电话' })
      fieldList.push({ type: 'string', value: 'delFlag', text: '逻辑删除标记' })
      fieldList.push({ type: 'number', value: 'weight', text: '重量' })
      fieldList.push({ type: 'string', value: 'piece', text: '件数' })
      fieldList.push({ type: 'string', value: 'transferIs', text: '是否中转 0否 1是' })
      fieldList.push({ type: 'number', value: 'transferMoney', text: '中转费' })
      fieldList.push({ type: 'number', value: 'premium', text: '保费' })
      fieldList.push({ type: 'number', value: 'backFare', text: '倒车费' })
      fieldList.push({ type: 'number', value: 'deliveryExpense', text: '送货费' })
      fieldList.push({ type: 'number', value: 'pickUpCharge', text: '接货费' })
      fieldList.push({ type: 'number', value: 'otherExpenses', text: '其他支出' })
      fieldList.push({ type: 'number', value: 'receivable', text: '应收合计' })
      fieldList.push({ type: 'string', value: 'type', text: '运输类型' })
      fieldList.push({ type: 'string', value: 'shippingMethod', text: '送货方式' })
      fieldList.push({ type: 'string', value: 'remark', text: '备注' })
      fieldList.push({ type: 'string', value: 'drawer', text: '开票员' })
      fieldList.push({ type: 'string', value: 'salesman', text: '业务员' })
      fieldList.push({ type: 'string', value: 'publicCode', text: '关联单号' })
      this.superFieldList = fieldList
    },
    // 当分页参数变化时触发的事件
    handlePageChange(event) {
      // 重新赋值
      this.ipagination.current = event.current
      this.ipagination.pageSize = event.pageSize
      // 查询数据
      this.loadData()
    },
    rowBack() {
      this.distributionPup = false
    },
    showDistribution(record){
      this.distributionPup = true
      this.$nextTick(() => {
        this.$refs.distributionPup.initpany(record.row)
      })
    }
  },
}
</script>
<style scoped>
.ant-table-footer {
  padding: 0px 0px;
}
@import '~@assets/less/common.less';
</style>
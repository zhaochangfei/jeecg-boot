<template>
  <!-- 查看配送单-->
  <div>
    <a-modal
      :maskClosable="false"
      centered
      :title="title"
      :width="1700"
      :visible="visible"
      @cancel="handleCancel"
      cancelText="关闭"
    >
      <a-row :gutter="10">
        <a-col :span="24">
          <a-card :bordered="false">
            <div class="table-operator">
              <a-button type="primary" icon="download" @click="handleExportXls('配送单')">导出</a-button> 
            </div>
            <!-- table区域-begin -->
            <div>
              <a-table
                ref="table"
                size="middle"
                :scroll="{ x: true }"
                bordered
                rowKey="id"
                :columns="columns"
                :dataSource="dataSource"
                :pagination="ipagination"
                :loading="loading"
                :rowSelection="{ selectedRowKeys: selectedRowKeys, onChange: onSelectChange }"
                class="j-table-force-nowrap"
                @change="handleTableChange"
              >
              </a-table>
            </div>
          </a-card>
        </a-col>
      </a-row>
      <template slot="footer">
        <a-button @click="handleCancel"> 关闭 </a-button>
      </template>
    </a-modal>
  </div>
</template>

<script>
import '@/assets/less/TableExpand.less'
import { mixinDevice } from '@/utils/mixin'
import { JeecgListMixin } from '@/mixins/JeecgListMixin'

export default {
  // import引入的组件
  components: {},
  name: 'DistributionPup',
  mixins: [JeecgListMixin, mixinDevice],
  data() {
    return {
      labelCol: {
        xs: { span: 24 },
        sm: { span: 4 },
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 16 },
      },
      title: '查看配送单',
      visible: false,
      model: {
        cpInStoreOrder: {},
      },
      // 表头
      columns: [
        {
          title: '单号',
          align: 'center',
          dataIndex: 'code',
        },
        {
          title: '单据日期',
          align: 'center',
          dataIndex: 'billdate',
          customRender: function (text) {
            return !text ? '' : text.length > 10 ? text.substr(0, 10) : text
          },
        },
        {
          title: '状态',
          align: 'center',
          dataIndex: 'sstatus_dictText',
        },
        {
          title: '起运站',
          align: 'center',
          dataIndex: 'originatingStation',
        },
        {
          title: '发货人',
          align: 'center',
          dataIndex: 'consignorId_dictText',
        },
        {
          title: '到达站',
          align: 'center',
          dataIndex: 'arrivalStation',
        },
        {
          title: '收货人',
          align: 'center',
          dataIndex: 'consigneeId_dictText',
        },
        {
          title: '总运费（元）',
          align: 'center',
          dataIndex: 'sumMoney',
        },
        {
          title: '车辆',
          align: 'center',
          dataIndex: 'carId_dictText',
        },
        {
          title: '件数',
          align: 'center',
          dataIndex: 'piece',
        },
        {
          title: '重量（kg）',
          align: 'center',
          dataIndex: 'weight',
        },
        {
          title: '备注',
          align: 'center',
          dataIndex: 'remark',
        },
      ],
      isorter: {
        column: 'createTime',
        order: 'desc',
      },
      url: {
        list: '/wms/wmsDistribution/deriveDistributionList',
        exportXlsUrl: '/wms/wmsDistribution/exportXls',
      },
      dictOptions: {},
      superFieldList: [],
    }
  },
  created() {
    this.getSuperFieldList()
  },
  // 方法集合
  methods: {
    handleCancel() {
      this.close()
    },
    initpany(record) {
      this.visible = true
      this.queryParam = {
        carId: record.carId,
        billdate: record.billdate,
        carIphone: record.carIphone,
        originatingStation: record.originatingStation,
      }
      this.loadData()
    },
    close() {
      this.$emit('close')
      this.visible = false
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
  },
  // 生命周期 - 挂载完成（可以访问DOM元素）
  mounted() {},
}
</script>
<style lang="less" scoped>
@import '~@assets/less/common.less';
</style>

<template>
  <a-spin :spinning="confirmLoading">
    <j-form-container :disabled="formDisabled">
      <a-form-model ref="form" :model="model" :rules="validatorRules" slot="detail">
        <div class="fx_list">
          <a-row>
            <a-col :span="8">
              <a-form-model-item label="开票日期" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="billdate">
                <j-date placeholder="请选择开票日期" v-model="model.billdate" style="width: 100%" />
              </a-form-model-item>
            </a-col>
            <a-col :span="8">
              <a-form-model-item label="手输单号" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="code">
                <a-input v-model="model.code" disabled placeholder="不填则自动生成"></a-input>
              </a-form-model-item>
            </a-col>
            <a-col :span="8">
              <a-form-model-item label="经办人" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="operator">
                <a-input v-model="model.operator" placeholder="请输入经办人"></a-input>
              </a-form-model-item>
            </a-col>
          </a-row>
          <a-row> </a-row>
          <a-row>
            <a-col :span="8">
              <a-form-model-item
                label="发货人"
                :labelCol="labelCol"
                :wrapperCol="wrapperCol"
                prop="consignorId_dictText"
              >
                <a-input-group compact>
                  <a-button @click="showConsignor" icon="dash"></a-button>
                  <a-input
                    style="width: 290px"
                    v-model="model.consignorId_dictText"
                    onfocus="this.blur()"
                    placeholder="请选择"
                    allow-clear
                  />
                </a-input-group>
              </a-form-model-item>
            </a-col>
            <a-col :span="8">
              <a-form-model-item
                label="发货人电话"
                :labelCol="labelCol"
                :wrapperCol="wrapperCol"
                prop="consignorIphone"
              >
                <a-input v-model="model.consignorIphone" placeholder="请输入发货人电话"></a-input>
              </a-form-model-item>
            </a-col>
            <a-col :span="8">
              <a-form-model-item label="起运站" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="originatingStation">
                <a-input v-model="model.originatingStation" placeholder="请输入起运站"></a-input>
              </a-form-model-item>
            </a-col>
          </a-row>
          <a-row>
            <a-col :span="8">
              <a-form-model-item
                label="收货人"
                :labelCol="labelCol"
                :wrapperCol="wrapperCol"
                prop="consigneeId_dictText"
              >
                <a-input-group compact>
                  <a-button @click="showConsignee" icon="dash"></a-button>
                  <a-input
                    style="width: 290px"
                    v-model="model.consigneeId_dictText"
                    onfocus="this.blur()"
                    placeholder="请选择"
                    allow-clear
                  />
                </a-input-group>
              </a-form-model-item>
            </a-col>
            <a-col :span="8">
              <a-form-model-item
                label="收货人电话"
                :labelCol="labelCol"
                :wrapperCol="wrapperCol"
                prop="consigneeIphone"
              >
                <a-input v-model="model.consigneeIphone" placeholder="请输入联系电话"></a-input>
              </a-form-model-item>
            </a-col>
            <a-col :span="8">
              <a-form-model-item label="到达站" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="arrivalStation">
                <a-input v-model="model.arrivalStation" placeholder="请输入到达站"></a-input>
              </a-form-model-item>
            </a-col>
          </a-row>
          <a-row>
            <a-col :span="8">
              <a-form-model-item label="车辆" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="carId_dictText">
                <a-input-group compact>
                  <a-button @click="showCar('0')" icon="dash"></a-button>
                  <a-input
                    style="width: 290px"
                    v-model="model.carId_dictText"
                    onfocus="this.blur()"
                    placeholder="请选择车辆"
                    allow-clear
                  />
                </a-input-group>
              </a-form-model-item>
            </a-col>
            <a-col :span="8">
              <a-form-model-item label="车辆电话" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="carIphone">
                <a-input v-model="model.carIphone" placeholder="请输入车辆电话"></a-input>
              </a-form-model-item>
            </a-col>
            <a-col :span="8">
              <a-form-model-item label="司机" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="carDriver">
                <a-input v-model="model.carDriver" placeholder="请输入司机"></a-input>
              </a-form-model-item>
            </a-col>
          </a-row>

          <a-row>
            <a-col :span="8">
              <a-form-model-item label="现付" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="spotPayment">
                <a-input-number v-model="model.spotPayment" placeholder="请输入现付" style="width: 100%" />
              </a-form-model-item>
            </a-col>
            <a-col :span="8">
              <a-form-model-item label="预付" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="prepay">
                <a-input-number v-model="model.prepay" placeholder="请输入预付（代收货款）" style="width: 100%" />
              </a-form-model-item>
            </a-col>
            <a-col :span="8">
              <a-form-model-item label="提付" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="withdrawal">
                <a-input-number v-model="model.withdrawal" placeholder="请输入提付" style="width: 100%" />
              </a-form-model-item>
            </a-col>
          </a-row>
          <a-row>
            <a-col :span="8">
              <a-form-model-item
                label="总运费"
                :labelCol="labelCol"
                :wrapperCol="wrapperCol"
                prop="sumMoney"
              >
                <a-input v-model="model.sumMoney = zDemandNum" placeholder="请输入总运费（费用合计）"></a-input>
              </a-form-model-item>
            </a-col>

            <a-col :span="8">
              <a-form-model-item label="重量" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="weight">
                <a-input-number v-model="model.weight" placeholder="请输入重量" style="width: 100%" />
              </a-form-model-item>
            </a-col>
            <a-col :span="8">
              <a-form-model-item label="件数" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="piece">
                <a-input v-model="model.piece" placeholder="请输入件数"></a-input>
              </a-form-model-item>
            </a-col>
          </a-row>
          <a-row>
            <a-col :span="8">
              <a-form-model-item
                label="是否中转"
                :labelCol="labelCol"
                :wrapperCol="wrapperCol"
                prop="transferIs"
              >
              <j-dict-select-tag type="list" v-model="model.transferIs" dictCode="transferIs" placeholder="请选择是否中转" />
              </a-form-model-item>
            </a-col>
            <a-col :span="8">
              <a-form-model-item label="中转费" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="transferMoney">
                <a-input-number v-model="model.transferMoney" placeholder="请输入中转费" style="width: 100%" />
              </a-form-model-item>
            </a-col>
            <a-col :span="8">
              <a-form-model-item label="送货方式" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="shippingMethod">
                <j-dict-select-tag type="list" v-model="model.shippingMethod" dictCode="shippingMethod" placeholder="请选择送货方式" />
              </a-form-model-item>
            </a-col>
          </a-row>
          <a-row>
            <span v-if="this.model.transferIs=='1'">
              <a-col :span="8">
              <a-form-model-item label="中转车辆" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="carIdTransfer_dictText">
                <a-input-group compact>
                  <a-button @click="showCar('1')" icon="dash"></a-button>
                  <a-input
                    style="width: 290px"
                    v-model="model.carIdTransfer_dictText"
                    onfocus="this.blur()"
                    placeholder="请选择中转车辆"
                    allow-clear
                  />
                </a-input-group>
              </a-form-model-item>
            </a-col>
            <a-col :span="8">
              <a-form-model-item label="中转车辆电话" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="carIphoneTransfer">
                <a-input v-model="model.carIphoneTransfer" placeholder="请输入中转车辆电话"></a-input>
              </a-form-model-item>
            </a-col>
            <a-col :span="8">
              <a-form-model-item label="中转司机" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="carDriverTransfer">
                <a-input v-model="model.carDriverTransfer" placeholder="请输入中转司机"></a-input>
              </a-form-model-item>
            </a-col>
            </span>
          </a-row>
          <a-row>
            <a-col :span="8">
              <a-form-model-item label="其他支出" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="otherExpenses">
                <a-input-number v-model="model.otherExpenses" placeholder="请输入其他支出" style="width: 100%" />
              </a-form-model-item>
            </a-col>
            <a-col :span="8">
              <a-form-model-item label="应收合计" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="receivable">
                <a-input-number v-model="model.receivable" placeholder="请输入应收合计" style="width: 100%" />
              </a-form-model-item>
            </a-col>
            <a-col :span="8">
              <a-form-model-item label="运输类型" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="type">
                <j-dict-select-tag type="list" v-model="model.type" dictCode="di_type" placeholder="请输入运输类型" />
              </a-form-model-item>
            </a-col>
          </a-row>
          <a-row>
            <a-col :span="24">
              <a-form-model-item label="备注" :labelCol="labelCol2" :wrapperCol="wrapperCol" prop="remark">
                <a-textarea v-model="model.remark" rows="4" placeholder="请输入备注" />
              </a-form-model-item>
            </a-col>
          </a-row>
        </div>
        <div class="fx_list">
          <h2>货物明细</h2>

          <a-row :gutter="10">
            <a-col :span="24">
              <div class="table-operator">
                <a-button @click="onAdd()" type="primary" icon="plus">新增</a-button>
              </div>
              <a-table
                ref="table"
                size="middle"
                id="keytabel"
                bordered
                :columns="columns"
                :dataSource="details"
                :pagination="false"
                :loading="loading"
                rowKey="pibuItemName"
                class="j-table-force-nowrap"
              >
                <span slot="goodsName" slot-scope="text, record">
                  <a-input v-model="record.goodsName" />
                </span>
                <span slot="packaging" slot-scope="text, record">
                  <a-input v-model="record.packaging" />
                </span>
                <span slot="number" slot-scope="text, record">
                  <a-input v-model="record.number" />
                </span>
                <span slot="quantity" slot-scope="text, record">
                  <a-input v-model="record.quantity" />
                </span>
                <span slot="remark" slot-scope="text, record">
                  <a-input v-model="record.remark" />
                </span>
                <span slot="action" slot-scope="text, record, index">
                  <a-popconfirm title="确定删除吗?" @confirm="() => onDelete(index)" style="color: #f5222d">
                    <a>删除</a>
                  </a-popconfirm>
                </span>
              </a-table>
            </a-col>
          </a-row>
        </div>
      </a-form-model>
    </j-form-container>
    <!-- 选择发货人员 -->
    <wmsConsignorUp ref="wmsConsignorUp" v-if="consignorUp" @wmsConsignorUp="wmsConsignorUp"></wmsConsignorUp>
    <!-- 选择收货人员 -->
    <wmsConsigneeUp ref="wmsConsigneeUp" v-if="consigneeUp" @wmsConsigneeUp="wmsConsigneeUp"></wmsConsigneeUp>
    <!-- 选择车辆 -->
    <wmsCarUp ref="wmsCarUp" v-if="carUp" @wmsCarUp="wmsCarUp"></wmsCarUp>
  </a-spin>
</template>

<script>
import { httpAction, getAction } from '@/api/manage'
import { queryWmsDistributionDetails } from '@/api/wmswarehouse'
import { validateDuplicateValue } from '@/utils/util'
import  wmsConsignorUp from '../pup/WmsConsignorUp'
import  wmsConsigneeUp from '../pup/WmsConsigneeUp'
import  wmsCarUp from '../pup/WmsCarUp'

export default {
  name: 'WmsDistributionForm',
  components: {
    wmsConsignorUp,
    wmsConsigneeUp,
    wmsCarUp
  },
  props: {
    //表单禁用
    disabled: {
      type: Boolean,
      default: false,
      required: false,
    },
  },
  data() {
    return {
      model: {},
      details: [],
      labelCol: {
        xs: { span: 24 },
        sm: { span: 5 },
      },
      labelCol2: {
        xs: { span: 24 },
        sm: { span: 2 },
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 16 },
      },
      
      loading: false,
      confirmLoading: false,
      validatorRules: {
        billdate: { required: true, message: '请选择开票日期' },
        consignorId_dictText: { required: true, message: '请选择发货人！' },
        consigneeId_dictText: { required: true, message: '请选择收货人！' },
        carId_dictText: { required: true, message: '请选择车辆！' },
        transferIs: { required: true, message: '请选择是否中转！' },
        carIdTransfer_dictText: { required: true, message: '请选择中转车辆!' },
        // code: { required: true, message: '请输入输单号!' },
        consignorIphone: { required: true, message: '请输入发货人电话!' },
        originatingStation: { required: true, message: '请输入起运站!' },
        consigneeIphone: { required: true, message: '请输入联系电话!' },
        arrivalStation: { required: true, message: '请输入到达站!' },
        carDriver: { required: true, message: '请输入司机!' },
        carIphone: { required: true, message: '请输入车辆电话!' },
        sumMoney: { required: true, message: '请输入总运费!' },
        weight: { required: true, message: '请输入重量!' },
        piece: { required: true, message: '请输入件数!' },
      },
      url: {
        add: '/wms/wmsDistribution/add',
        edit: '/wms/wmsDistribution/edit',
        queryById: '/wms/wmsDistribution/queryById',
      },
      columns: [
        {
          title: '序号',
          dataIndex: '',
          key: 'rowIndex',
          width: 60,
          align: 'center',
          customRender: function (t, r, index) {
            return parseInt(index) + 1
          },
        },
        {
          title: '货物名称',
          align: 'center',
          dataIndex: 'goodsName',
          scopedSlots: { customRender: 'goodsName' },
        },
        {
          title: '包装',
          align: 'center',
          dataIndex: 'packaging',
          scopedSlots: { customRender: 'packaging' },
        },

        {
          title: '数量',
          align: 'center',
          width: 100,
          dataIndex: 'number',
          scopedSlots: { customRender: 'number' },
        },
        {
          title: '重量',
          align: 'center',
          width: 100,
          dataIndex: 'quantity',
          scopedSlots: { customRender: 'quantity' },
        },
        {
          title: '备注',
          align: 'center',
          dataIndex: 'remark',
          scopedSlots: { customRender: 'remark' },
        },
        {
          title: '操作',
          dataIndex: 'action',
          align: 'center',
          scopedSlots: { customRender: 'action' },
        },
      ],
      consignorUp: false, //发货人弹窗
      consigneeUp: false, //收货人弹窗
      carUp: false, //车辆弹窗
      carFlag:'',//车辆弹窗标记
    }
  },
  computed: {
    formDisabled() {
      return this.disabled
    },
     zDemandNum() {
      if (isNaN(Number(this.model.spotPayment) +Number(this.model.prepay) + Number(this.model.withdrawal))) {
        return ''
      } else {
        return (Number(this.model.spotPayment) + Number(this.model.prepay) + Number(this.model.withdrawal)).toFixed(2)
      }
    },
  },
  created() {
    //备份model原始值
    this.modelDefault = JSON.parse(JSON.stringify(this.model))
  },
  methods: {
    add() {
      // this.edit(this.modelDefault)
    },
    edit(record) {
      this.model = Object.assign({}, record)
      queryWmsDistributionDetails({ distributionId: record.id,pageSize:1000 }).then(res => {
            if (res.result) {
              this.details = res.result.records
            }
          })
      this.visible = true
    },
    //打开发货人员弹窗
    showConsignor() {
      this.consignorUp = true
      this.$nextTick(() => {
        this.$refs.wmsConsignorUp.initpany()
      })
    },
    //发货人员回调接口
    wmsConsignorUp(data) {
      if (data) {
        this.$set(this.model, 'consignorId_dictText', data[0].name)
        this.$set(this.model, 'consignorId', data[0].id)
        this.$set(this.model, 'consignorIdcard', data[0].idcard)
        this.$set(this.model, 'consignorIphone', data[0].iphone)
      }
    },
    //打开收货人员弹窗
    showConsignee() {
      this.consigneeUp = true
      this.$nextTick(() => {
        this.$refs.wmsConsigneeUp.initpany()
      })
    },
    //收货人员回调接口
    wmsConsigneeUp(data) {
      if (data) {
        this.$set(this.model, 'consigneeId_dictText', data[0].name)
        this.$set(this.model, 'consigneeId', data[0].id)
        this.$set(this.model, 'consigneeIdcard', data[0].idcard)
        this.$set(this.model, 'consigneeIphone', data[0].iphone)
      }
    },
    //打开司机车辆弹窗
    showCar(flag) {
      this.carFlag = flag
      this.carUp = true
      this.$nextTick(() => {
        this.$refs.wmsCarUp.initpany()
      })
    },
    //司机车辆回调接口
    wmsCarUp(data) {
      if (data&&this.carFlag=='0') {
        this.$set(this.model, 'carId_dictText', data[0].licensePlateNumber)
        this.$set(this.model, 'carId', data[0].id)
        this.$set(this.model, 'carDriver', data[0].driver)
        this.$set(this.model, 'carIphone', data[0].iphone)
      }
      if (data&&this.carFlag=='1') {
        this.$set(this.model, 'carIdTransfer_dictText', data[0].licensePlateNumber)
        this.$set(this.model, 'carIdTransfer', data[0].id)
        this.$set(this.model, 'carDriverTransfer', data[0].driver)
        this.$set(this.model, 'carIphoneTransfer', data[0].iphone)
      }
    },
    //新增
    onAdd() {
      var detail = {
        name: '',
        iphone: '',
        idcard: '',
        address: '',
        remark: '',
      }
      this.details.push(detail)
      console.log('获取到的值detail：', detail)
    },
    //删除
    onDelete(i) {
      console.log(i)
      this.details.splice(i, 1)
    },
    submitForm() {
      this.model.details=this.details
      const that = this
      // 触发表单验证
      this.$refs.form.validate((valid) => {
        if (valid) {
          that.confirmLoading = true
          let httpurl = ''
          let method = ''
          if (!this.model.id) {
            httpurl += this.url.add
            method = 'post'
          } else {
            httpurl += this.url.edit
            method = 'put'
          }
          httpAction(httpurl, this.model, method)
            .then((res) => {
              if (res.success) {
                that.$message.success(res.message)
                that.$emit('ok')
              } else {
                that.$message.warning(res.message)
              }
            })
            .finally(() => {
              that.confirmLoading = false
            })
        }
      })
    },
  },
}
</script>
<style lang="less" scoped>
@import '~@assets/less/common.less';

.fx_list {
  h2 {
    padding: 0px 10px;
    line-height: 20px;
    position: relative;
    font-size: 15px;
    color: #000;
    &::before {
      content: '';
      position: absolute;
      z-index: 1;
      left: 0;
      top: 50%;
      margin-top: -6px;
      display: inline-block;
      width: 4px;
      height: 12px;
      background: #1d6cee;
    }
  }
  /deep/ .ant-form-item {
    margin-bottom: 20px;
    .ant-form-item-control {
      position: relative;
      .ant-form-explain {
        position: absolute;
        // left: 108px;
        // bottom: 10px;
      }
    }
  }
  .ft_table {
    padding: 10px;
    dl {
      border-left: 1px solid #e8e8e8;
      display: flex;
      justify-content: center;

      align-items: center;
      margin-bottom: 0;
      font-size: 14px;
      line-height: 1.5;
      &:first-child {
        background: #f6f6f6;
        border-top: 1px solid #e8e8e8;
        border-radius: 4px 4px 0 0;
      }
      &:last-child {
        border-radius: 0 0 4px 4px;
      }
      dt {
        border-right: 1px solid #e8e8e8;
        border-bottom: 1px solid #e8e8e8;
        color: rgba(0, 0, 0, 0.85);
        font-weight: 500;
        padding: 5px 5px;
        text-align: center;
        flex: 3;
        min-height: 36px;
        line-height: 36px;
        margin-bottom: 0;
        &:last-child {
          flex: 5;
        }
      }

      dd {
        border-right: 1px solid #e8e8e8;
        margin-bottom: 0;
        border-bottom: 1px solid #e8e8e8;
        color: rgba(0, 0, 0, 0.75);
        padding: 10px 4px;
        text-align: center;
        flex: 3;
        min-height: 54px;
        align-items: center;
        margin-bottom: 0;
        span {
          display: inline-block;
          line-height: 32px;
          margin-bottom: 0;
        }
        &:last-child {
          flex: 5;
        }
      }
    }
  }
}
</style>
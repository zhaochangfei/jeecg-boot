<template>
  <a-form-model :model='model'>
    <div class='no-print' style='text-align: right'>
      <a-button v-print="'#printContent'" ghost type='primary'>打印</a-button>
    </div>
    <section ref='print' id='printContent' class='abcdefg'>
      <div style='text-align: center'>
        <p style='color:#000;font-size: 30px;font-weight: 800'>新疆强盛冷链物流运单</p>
      </div>
      <a-row>
        <a-col :span='6'>
          <span>NO:</span>
          <span>{{ model.code }}</span>
        </a-col>
        <a-col :span='4'>
          <span>发站:</span>
          <span style='margin-left: 10px'>{{ model.originatingStation }}</span>
        </a-col>
        <a-col :span='4'>
          <span>到站:</span>
          <span style='margin-left: 10px'>{{ model.arrivalStation }}</span>
        </a-col>
        <a-col :span='5'>
          <span>车辆:</span>
          <span style='margin-left: 10px'>{{model.carId_dictText}}</span>
        </a-col>
        <a-col :span='5' style='text-align: right'>
          <span>日期:</span>
          <span v-model='model.billdate' style='margin-left: 10px'>{{ model.billdate }}</span>
        </a-col>
      </a-row>
      <a-row style='border: 1px solid #000'>
        <a-col :span='1'
               style='border-width:0 1px 0 0; border-color: #000; border-style:solid; text-align: center'>
          <div style='height: 30px; padding-top: 4px'>
            <span>发</span>
          </div>
          <div style='height: 30px; padding-top: 2px'>
            <span>货</span>
          </div>
          <div style='height: 30px; padding-top: 2px'>
            <span>方</span>
          </div>
        </a-col>
        <a-col :span='5' style='text-align: center'>
          <div
            style='border-width:0 1px 1px 0; border-color: #000; border-style:solid; height: 45px; padding-top: 10px'>
            <span>联系人</span>
          </div>
          <div style='border-width:0 1px 0 0; border-color: #000; border-style:solid; height: 45px; padding-top: 10px'>
            <span>电话</span>
          </div>
        </a-col>
        <a-col :span='6' style='text-align: center'>
          <div
            style='border-width:0 1px 1px 0; border-color: #000; border-style:solid; height: 45px; padding-top: 10px'>
            <span>{{ model.consignorName }}</span>
          </div>
          <div
            style='border-width:0 1px 0 0; border-color: #000; border-style:solid; height: 45px; padding-top: 10px'>
            <span>{{ model.consigneeIphone }}</span>
          </div>
        </a-col>
        <a-col :span='1'
               style='border-width:0 1px 0 0; border-color: #000; border-style:solid; text-align: center;'>
          <div style='height: 30px; padding-top: 4px'>
            <span>收</span>
          </div>
          <div style='height: 30px; padding-top: 2px'>
            <span>货</span>
          </div>
          <div style='height: 30px; padding-top: 2px'>
            <span>方</span>
          </div>
        </a-col>
        <a-col :span='5' style='text-align: center;'>
          <div
            style='border-width:0 1px 1px 0; border-color: #000; border-style:solid; height: 45px; padding-top: 10px'>
            <span>联系人</span>
          </div>
          <div style='border-width:0 1px 0 0; border-color: #000; border-style:solid; height: 45px; padding-top: 10px'>
            <span>电话</span>
          </div>
        </a-col>
        <a-col :span='6' style='text-align: center;'>
          <div
            style='border-width:0 0 1px 0; border-color: #000; border-style:solid; height: 45px; padding-top: 10px'>
            <span>{{ model.consigneeName }}</span>
          </div>
          <div style='height: 45px; padding-top: 10px'>
            <span>{{ model.consigneeIphone }}</span>
          </div>
        </a-col>
      </a-row>
      <a-row style='border-width:0 1px 1px 1px; border-color: #000; border-style:solid'>
        <a-col :span='6'
               style='border-width:0 1px 0 0; border-color: #000; border-style:solid; text-align: center; height: 30px'>
          <span>货物名称</span>
        </a-col>
        <a-col :span='4'
               style='border-width:0 1px 0 0; border-color: #000; border-style:solid; text-align: center; height: 30px'>
          <span>包装</span>
        </a-col>
        <a-col :span='4'
               style='border-width:0 1px 0 0; border-color: #000; border-style:solid; text-align: center; height: 30px'>
          <span>数量</span>
        </a-col>
        <a-col :span='4'
               style='border-width:0 1px 0 0; border-color: #000; border-style:solid; text-align: center; height: 30px'>
          <span>重量(kg)</span>
        </a-col>
        <a-col :span='6' style='text-align: center; height: 30px'>
          <span>运费(元)</span>
        </a-col>
      </a-row>
      <a-row style='border-width:0 1px 1px 1px; border-color: #000; border-style:solid'>
        <a-col :span='6'
               style='border-width:0 1px 0 0; border-color: #000; border-style:solid; text-align: center; height: 30px'>
          <span v-if='details.length>0'>{{ details[0].goodsName }}</span>
        </a-col>
        <a-col :span='4'
               style='border-width:0 1px 0 0; border-color: #000; border-style:solid; text-align: center; height: 30px'>
          <span v-if='details.length>0'>{{ details[0].packaging }}</span>
        </a-col>
        <a-col :span='4'
               style='border-width:0 1px 0 0; border-color: #000; border-style:solid; text-align: center; height: 30px'>
          <span v-if='details.length>0'>{{ details[0].number }}</span>
        </a-col>
        <a-col :span='4'
               style='border-width:0 1px 0 0; border-color: #000; border-style:solid; text-align: center; height: 30px'>
          <span v-if='details.length>0'>{{ details[0].quantity }}</span>
        </a-col>
        <a-col :span='6' style='text-align: center; height: 30px'>
          <span v-if='details.length>0'>{{ details[0].freight }}</span>
        </a-col>
      </a-row>
      <a-row style='border-width:0 1px 1px 1px; border-color: #000; border-style:solid'>
        <a-col :span='6'
               style='border-width:0 1px 0 0; border-color: #000; border-style:solid; text-align: center; height: 30px'>
          <span v-if='details.length>1'>{{ details[1].goodsName }}</span>
        </a-col>
        <a-col :span='4'
               style='border-width:0 1px 0 0; border-color: #000; border-style:solid; text-align: center; height: 30px'>
          <span v-if='details.length>1'>{{ details[1].packaging }}</span>
        </a-col>
        <a-col :span='4'
               style='border-width:0 1px 0 0; border-color: #000; border-style:solid; text-align: center; height: 30px'>
          <span v-if='details.length>1'>{{ details[0].number }}</span>
        </a-col>
        <a-col :span='4'
               style='border-width:0 1px 0 0; border-color: #000; border-style:solid; text-align: center; height: 30px'>
          <span v-if='details.length>1'>{{ details[0].quantity }}</span>
        </a-col>
        <a-col :span='6' style='text-align: center; height: 30px'>
          <span v-if='details.length>1'>{{ details[0].freight }}</span>
        </a-col>
      </a-row>
      <a-row style='border-width:0 1px 1px 1px; border-color: #000; border-style:solid'>
        <a-col :span='4'
               style='border-width:0 1px 0 0; border-color: #000; border-style:solid; text-align: center; height: 30px'>
          <div style='display: inline-block; vertical-align: middle; text-align: center'>
            <span>现付</span>
          </div>
        </a-col>
        <a-col :span='4'
               style='border-width:0 1px 0 0; border-color: #000; border-style:solid;  text-align: center; height: 30px'>
          <span>{{ model.spotPayment }}</span>
        </a-col>
        <a-col :span='4'
               style='border-width:0 1px 0 0; border-color: #000; border-style:solid;  text-align: center; height: 30px'>
          <span>预付</span>
        </a-col>
        <a-col :span='4'
               style='border-width:0 1px 0 0; border-color: #000; border-style:solid;  text-align: center; height: 30px'>
          <span>{{ model.prepay }}</span>
        </a-col>
        <a-col :span='4'
               style='border-width:0 1px 0 0; border-color: #000; border-style:solid;  text-align: center; height: 30px'>
          <span>提付</span>
        </a-col>
        <a-col :span='4' style='text-align: center; height: 30px'>
          <span>{{ model.withdrawal }}</span>
        </a-col>
      </a-row>
      <a-row style='border-width:0 1px 1px 1px; border-color: #000; border-style:solid'>
        <a-col :span='4'
               style='border-width:0 1px 0 0; border-color: #000; border-style:solid; text-align: center; height: 60px; padding-top: 18px'>
          <span>运费总金额(元)</span>
        </a-col>
        <a-col :span='6'
               style='border-width:0 1px 0 0; border-color: #000; border-style:solid; text-align: center; height: 60px; padding-top:18px '>
          <span>{{ model.sumMoney }}</span>
        </a-col>
        <a-col :span='4'
               style='border-width:0 1px 0 0; border-color: #000; border-style:solid; text-align: center; height: 60px; padding-top: 18px'>
          <span style='display: inline-block'>备注</span>
        </a-col>
        <a-col :span='10' style='height: 60px'>
          <div style='height: 60px; display: table-cell;vertical-align: middle;'>
            <span
              style='padding-left: 20px; padding-right: 20px; display:inline-block;word-break: break-all;white-space: normal;'>{{ model.remark
              }}</span>
          </div>
        </a-col>
      </a-row>
      <a-row style='border-width:0 1px 1px 1px; border-color: #000; border-style:solid'>
        <a-col :span='4'
               style='border-width:0 1px 0 0; border-color: #000; border-style:solid; text-align: center; height: 30px'>
          <span>司机:</span>
        </a-col>
        <a-col :span='4'
               style='border-width:0 1px 0 0; border-color: #000; border-style:solid;  text-align: center; height: 30px'>
          <span>{{ model.carDriver }}</span>
        </a-col>
        <a-col :span='4'
               style='border-width:0 1px 0 0; border-color: #000; border-style:solid;  text-align: center; height: 30px'>
          <span>车上电话:</span>
        </a-col>
        <a-col :span='4'
               style='border-width:0 1px 0 0; border-color: #000; border-style:solid;  text-align: center; height: 30px'>
          <span>{{ model.carIphone }}</span>
        </a-col>
        <a-col :span='4'
               style='border-width:0 1px 0 0; border-color: #000; border-style:solid;  text-align: center; height: 30px'>
          <span>托运人签字:</span>
        </a-col>
        <a-col :span='4' style='text-align: center; height: 30px'>
          <span></span>
        </a-col>
      </a-row>
      <a-row style='border-width:0 1px 1px 1px; border-color: #000; border-style:solid'>
        <a-col :span='4'
               style='border-width:0 1px 0 0; border-color: #000; border-style:solid; text-align: center; height: 30px'>
          <span>经办人:</span>
        </a-col>
        <a-col :span='4'
               style='border-width:0 1px 0 0; border-color: #000; border-style:solid;  text-align: center; height: 30px'>
          <span>{{ model.operator }}</span>
        </a-col>
        <a-col :span='8'
               style='border-width:0 1px 0 0; border-color: #000; border-style:solid;  text-align: center; height: 30px'>
        </a-col>
        <a-col :span='4'
               style='border-width:0 1px 0 0; border-color: #000; border-style:solid;  text-align: center; height: 30px'>
          <span>收货人签字:</span>
        </a-col>
        <a-col :span='4' style='text-align: center; height: 30px'>
          <span></span>
        </a-col>
      </a-row>
    </section>
  </a-form-model>
</template>

<script>
import wmsConsignorUp from '@views/modules/wms/pup/WmsConsignorUp.vue'
import wmsConsigneeUp from '@views/modules/wms/pup/WmsConsigneeUp.vue'
import wmsCarUp from '@views/modules/wms/pup/WmsCarUp.vue'
import { queryWmsDistributionDetails } from '@api/wmswarehouse'
import { httpAction } from '@api/manage'

export default {
  name: 'WmsTransOrderForm',

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
      required: false
    }
  },
  data() {
    return {
      model: {},
      details: [],
      labelCol: {
        xs: { span: 24 },
        sm: { span: 5 }
      },
      labelCol2: {
        xs: { span: 24 },
        sm: { span: 2 }
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 16 }
      },

      loading: false,
      confirmLoading: false,
      validatorRules: {
        billdate: { required: true, message: '请选择开票日期' },
        consignorId_dictText: { required: true, message: '请选择发货人！' },
        consigneeId_dictText: { required: true, message: '请选择收货人！' },
        carId_dictText: { required: true, message: '请选择车辆！' },
        transferIs: { required: true, message: '请选择是否中转！' },
        // carIdTransfer_dictText: { required: true, message: '请选择中转车辆!' },
        // code: { required: true, message: '请输入输单号!' },
        consignorIphone: { required: true, message: '请输入发货人电话!' },
        originatingStation: { required: true, message: '请输入起运站!' },
        consigneeIphone: { required: true, message: '请输入联系电话!' },
        arrivalStation: { required: true, message: '请输入到达站!' },
        carDriver: { required: true, message: '请输入司机!' },
        carIphone: { required: true, message: '请输入车辆电话!' },
        sumMoney: { required: true, message: '请输入总运费!' },
        weight: { required: true, message: '请输入重量!' },
        piece: { required: true, message: '请输入件数!' }
      },
      url: {
        add: '/wms/wmsDistribution/add',
        edit: '/wms/wmsDistribution/edit',
        queryById: '/wms/wmsDistribution/queryById'
      },
      columns: [
        {
          title: '序号',
          dataIndex: '',
          key: 'rowIndex',
          width: 60,
          align: 'center',
          customRender: function(t, r, index) {
            return parseInt(index) + 1
          }
        },
        {
          title: '货物名称',
          align: 'center',
          dataIndex: 'goodsName',
          scopedSlots: { customRender: 'goodsName' }
        },
        {
          title: '包装',
          align: 'center',
          dataIndex: 'packaging',
          scopedSlots: { customRender: 'packaging' }
        },

        {
          title: '数量',
          align: 'center',
          width: 100,
          dataIndex: 'number',
          scopedSlots: { customRender: 'number' }
        },
        {
          title: '重量(kg)',
          align: 'center',
          width: 100,
          dataIndex: 'quantity',
          scopedSlots: { customRender: 'quantity' }
        },
        {
          title: '运费',
          align: 'center',
          width: 100,
          dataIndex: 'freight',
          scopedSlots: { customRender: 'freight' }
        }
      ],
      consignorUp: false, //发货人弹窗
      consigneeUp: false, //收货人弹窗
      carUp: false, //车辆弹窗
      carFlag: ''//车辆弹窗标记
    }
  },
  computed: {
    formDisabled() {
      return this.disabled
    },
    hasItems() {
      return this.details && this.details.length > 0 // 判断items是否为空
    },
    zDemandNum() {
      if (this.hasItems) {
        var bb = []
        for (var i in this.details.map(row => row.freight)) {
          if (this.details.map(row => row.freight)[i]) {
            bb.push(this.details.map(row => row.freight)[i])
          }
        }
        return bb.reduce((acc, cur) => parseFloat(cur) + acc, 0)
      } else {
        return 0 // 如果items为空，返回0
      }
      // if (isNaN(Number(this.model.spotPayment) +Number(this.model.prepay) + Number(this.model.withdrawal))) {
      //   return ''
      // } else {
      //   return (Number(this.model.spotPayment) + Number(this.model.prepay) + Number(this.model.withdrawal)).toFixed(2)
      // }
    }
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
      this.model.consigneeName = record.consigneeId_dictText
      this.model.consignorName = record.consignorId_dictText
      queryWmsDistributionDetails({ distributionId: record.id, pageSize: 1000 }).then(res => {
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
        this.$set(this.model, 'consignorName', data[0].name)
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
        this.$set(this.model, 'consigneeName', data[0].name)
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
      if (data && this.carFlag == '0') {
        this.$set(this.model, 'carId_dictText', data[0].licensePlateNumber)
        this.$set(this.model, 'carId', data[0].id)
        this.$set(this.model, 'carDriver', data[0].driver)
        this.$set(this.model, 'carIphone', data[0].iphone)
      }
      if (data && this.carFlag == '1') {
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
        remark: ''
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
      this.model.details = this.details
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
    }
  }
}
</script>

<style scoped>
span {
  color: black;
  font-weight: bold;
}
/*@page{*/
/*  size:  auto;   !* auto is the initial value *!*/
/*  margin: 3mm;  !* this affects the margin in the printer settings *!*/
/*}*/
</style>
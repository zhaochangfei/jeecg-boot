<template>
  <a-spin :spinning="confirmLoading">
    <j-form-container :disabled="formDisabled">
      <a-form-model ref="form" :model="model" :rules="validatorRules" slot="detail">
        <a-row>
          <a-col :span="24">
            <a-form-model-item label="单号" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="code">
              <a-input v-model="model.code" placeholder="请输入单号"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="单据日期" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="billdate">
              <j-date placeholder="请选择单据日期" v-model="model.billdate"  style="width: 100%" />
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="起运站" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="originatingStation">
              <a-input v-model="model.originatingStation" placeholder="请输入起运站"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="发货人id" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="consignorId">
              <a-input v-model="model.consignorId" placeholder="请输入发货人id"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="发货人身份证号" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="consignorIdcard">
              <a-input v-model="model.consignorIdcard" placeholder="请输入发货人身份证号"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="发货人电话" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="consignorIphone">
              <a-input v-model="model.consignorIphone" placeholder="请输入发货人电话"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="到达站" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="arrivalStation">
              <a-input v-model="model.arrivalStation" placeholder="请输入到达站"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="收货人id" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="consigneeId">
              <a-input v-model="model.consigneeId" placeholder="请输入收货人id"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="收货人电话" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="consigneeIphone">
              <a-input v-model="model.consigneeIphone" placeholder="请输入收货人电话"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="现付" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="spotPayment">
              <a-input-number v-model="model.spotPayment" placeholder="请输入现付" style="width: 100%" />
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="预付（代收货款）" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="prepay">
              <a-input-number v-model="model.prepay" placeholder="请输入预付（代收货款）" style="width: 100%" />
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="提付" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="withdrawal">
              <a-input-number v-model="model.withdrawal" placeholder="请输入提付" style="width: 100%" />
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="总运费（费用合计）" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="sumMoney">
              <a-input v-model="model.sumMoney" placeholder="请输入总运费（费用合计）"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="经办人" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="operator">
              <a-input v-model="model.operator" placeholder="请输入经办人"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="车辆Id" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="carId">
              <a-input v-model="model.carId" placeholder="请输入车辆Id"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="车辆电话" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="carIphone">
              <a-input v-model="model.carIphone" placeholder="请输入车辆电话"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="逻辑删除标记" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="delFlag">
              <a-input v-model="model.delFlag" placeholder="请输入逻辑删除标记"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="重量" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="weight">
              <a-input-number v-model="model.weight" placeholder="请输入重量" style="width: 100%" />
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="件数" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="piece">
              <a-input v-model="model.piece" placeholder="请输入件数"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="是否中转 0否 1是" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="transferIs">
              <a-input v-model="model.transferIs" placeholder="请输入是否中转 0否 1是"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="中转费" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="transferMoney">
              <a-input-number v-model="model.transferMoney" placeholder="请输入中转费" style="width: 100%" />
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="保费" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="premium">
              <a-input-number v-model="model.premium" placeholder="请输入保费" style="width: 100%" />
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="倒车费" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="backFare">
              <a-input-number v-model="model.backFare" placeholder="请输入倒车费" style="width: 100%" />
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="送货费" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="deliveryExpense">
              <a-input-number v-model="model.deliveryExpense" placeholder="请输入送货费" style="width: 100%" />
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="接货费" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="pickUpCharge">
              <a-input-number v-model="model.pickUpCharge" placeholder="请输入接货费" style="width: 100%" />
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="其他支出" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="otherExpenses">
              <a-input-number v-model="model.otherExpenses" placeholder="请输入其他支出" style="width: 100%" />
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="应收合计" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="receivable">
              <a-input-number v-model="model.receivable" placeholder="请输入应收合计" style="width: 100%" />
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="运输类型" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="type">
              <a-input v-model="model.type" placeholder="请输入运输类型"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="送货方式" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="shippingMethod">
              <a-input v-model="model.shippingMethod" placeholder="请输入送货方式"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="备注" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="remark">
              <a-textarea v-model="model.remark" rows="4" placeholder="请输入备注" />
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="开票员" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="drawer">
              <a-input v-model="model.drawer" placeholder="请输入开票员"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="业务员" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="salesman">
              <a-input v-model="model.salesman" placeholder="请输入业务员"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="关联单号" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="publicCode">
              <a-input v-model="model.publicCode" placeholder="请输入关联单号"  ></a-input>
            </a-form-model-item>
          </a-col>
        </a-row>
      </a-form-model>
    </j-form-container>
  </a-spin>
</template>

<script>

  import { httpAction, getAction } from '@/api/manage'
  import { validateDuplicateValue } from '@/utils/util'

  export default {
    name: 'WmsDistributionForm',
    components: {
    },
    props: {
      //表单禁用
      disabled: {
        type: Boolean,
        default: false,
        required: false
      }
    },
    data () {
      return {
        model:{
         },
        labelCol: {
          xs: { span: 24 },
          sm: { span: 5 },
        },
        wrapperCol: {
          xs: { span: 24 },
          sm: { span: 16 },
        },
        confirmLoading: false,
        validatorRules: {
        },
        url: {
          add: "/wms/wmsDistribution/add",
          edit: "/wms/wmsDistribution/edit",
          queryById: "/wms/wmsDistribution/queryById"
        }
      }
    },
    computed: {
      formDisabled(){
        return this.disabled
      },
    },
    created () {
       //备份model原始值
      this.modelDefault = JSON.parse(JSON.stringify(this.model));
    },
    methods: {
      add () {
        this.edit(this.modelDefault);
      },
      edit (record) {
        this.model = Object.assign({}, record);
        this.visible = true;
      },
      submitForm () {
        const that = this;
        // 触发表单验证
        this.$refs.form.validate(valid => {
          if (valid) {
            that.confirmLoading = true;
            let httpurl = '';
            let method = '';
            if(!this.model.id){
              httpurl+=this.url.add;
              method = 'post';
            }else{
              httpurl+=this.url.edit;
               method = 'put';
            }
            httpAction(httpurl,this.model,method).then((res)=>{
              if(res.success){
                that.$message.success(res.message);
                that.$emit('ok');
              }else{
                that.$message.warning(res.message);
              }
            }).finally(() => {
              that.confirmLoading = false;
            })
          }
         
        })
      },
    }
  }
</script>
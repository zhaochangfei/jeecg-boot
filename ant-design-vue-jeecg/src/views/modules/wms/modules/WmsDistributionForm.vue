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
              <a-input v-model="model.spotPayment" placeholder="请输入现付"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="预付" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="prepay">
              <a-input v-model="model.prepay" placeholder="请输入预付"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="提付" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="withdrawal">
              <a-input v-model="model.withdrawal" placeholder="请输入提付"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="总运费" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="sumMoney">
              <a-input v-model="model.sumMoney" placeholder="请输入总运费"  ></a-input>
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
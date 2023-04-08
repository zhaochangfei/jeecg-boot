<template>
  <a-spin :spinning="confirmLoading">
    <j-form-container :disabled="formDisabled">
      <a-form-model ref="form" :model="model" :rules="validatorRules" slot="detail">
        <a-row>
          <a-col :span="24">
            <a-form-model-item label="驾驶员" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="driver">
              <a-input v-model="model.driver" placeholder="请输入驾驶员"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="电话" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="iphone">
              <a-input v-model="model.iphone" placeholder="请输入电话"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="车牌号" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="licensePlateNumber">
              <a-input v-model="model.licensePlateNumber" placeholder="请输入车牌号"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="司机状态" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="sstatus">
              <a-input v-model="model.sstatus" placeholder="请输入司机状态"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="车辆类型" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="carType">
              <a-input v-model="model.carType" placeholder="请输入车辆类型"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="车牌颜色" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="carColor">
              <a-input v-model="model.carColor" placeholder="请输入车牌颜色"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="身份证号码" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="idCard">
              <a-input v-model="model.idCard" placeholder="请输入身份证号码"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="道路运输证号码" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="rsqas">
              <a-input v-model="model.rsqas" placeholder="请输入道路运输证号码"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="驾驶证号" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="drivingLicence">
              <a-input v-model="model.drivingLicence" placeholder="请输入驾驶证号"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="车辆状态" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="carStatus">
              <a-input v-model="model.carStatus" placeholder="请输入车辆状态"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="开户行" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="openingBank">
              <a-input v-model="model.openingBank" placeholder="请输入开户行"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="银行卡号" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="bankCard">
              <a-input v-model="model.bankCard" placeholder="请输入银行卡号"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="是否绑定GPS" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="gps">
              <a-input v-model="model.gps" placeholder="请输入是否绑定GPS"  ></a-input>
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
    name: 'WmsCarForm',
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
          add: "/wms/wmsCar/add",
          edit: "/wms/wmsCar/edit",
          queryById: "/wms/wmsCar/queryById"
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
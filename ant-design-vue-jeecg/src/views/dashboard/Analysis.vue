<template>
  <div class="home-index">
    <img src="@/assets/qs.png" alt="#" />
  </div>
</template>
<script>
  import moment from 'moment'
  import 'moment/locale/zh-cn'
  moment.locale('zh-cn')
  export default {
    name: 'Analysis',
    mixins: {},
    data() {
      return {
      }
    },
    created() {
    },
    methods: {
     
    }
  }
</script>
<style lang="less" scoped>
// @import '~@assets/less/common.less';
div img {
  width: auto;
  height: auto;
  max-width: 100%;
  max-height: 100%;
  object-fit: fill;
}
</style>

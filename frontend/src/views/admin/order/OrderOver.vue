<template>
  <a-modal v-model="show" title="订单详情" @cancel="onClose" :width="1000">
    <template slot="footer">
      <a-button key="over" @click="overOrder" :loading="loading">
        完成结算
      </a-button>
      <a-button key="back" @click="onClose" type="danger">
        关闭
      </a-button>
    </template>
    <div style="font-size: 13px;font-family: SimHei" v-if="orderData !== null">
      <div style="padding-left: 24px;padding-right: 24px;margin-bottom: 50px;margin-top: 50px">
        <a-steps :current="current" progress-dot size="small">
          <a-step title="未支付" />
          <a-step title="已支付" />
          <a-step title="归还中" />
          <a-step title="已完成" />
        </a-steps>
      </div>
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col style="margin-bottom: 15px"><span style="font-size: 15px;font-weight: 650;color: #000c17">用户信息</span></a-col>
        <a-col :span="8"><b>用户编号：</b>
          {{ orderData.code ? orderData.code : '- -' }}
        </a-col>
        <a-col :span="8"><b>用户名称：</b>
          {{ orderData.name ? orderData.name : '- -' }}
        </a-col>
        <a-col :span="8"><b>联系方式：</b>
          {{ orderData.phone }}
        </a-col>
      </a-row>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col style="margin-bottom: 15px"><span style="font-size: 15px;font-weight: 650;color: #000c17">器材信息</span></a-col>
        <a-col :span="8"><b>器材编号：</b>
          {{ deviceInfo.code ? deviceInfo.code : '- -' }}
        </a-col>
        <a-col :span="8"><b>器材名称：</b>
          {{ deviceInfo.name ? deviceInfo.name : '- -' }}
        </a-col>
        <a-col :span="8"><b>型号：</b>
          {{ deviceInfo.model ? deviceInfo.model : '- -' }}
        </a-col>
      </a-row>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col :span="8"><b>品牌：</b>
          {{ deviceInfo.brand ? deviceInfo.brand : '- -' }}
        </a-col>
        <a-col :span="8"><b>负责人：</b>
          {{ deviceInfo.chargePerson ? deviceInfo.chargePerson : '- -' }}
        </a-col>
      </a-row>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col style="margin-bottom: 15px"><span style="font-size: 15px;font-weight: 650;color: #000c17">器材图片</span></a-col>
        <a-col :span="24">
          <a-upload
            name="avatar"
            action="http://127.0.0.1:9527/file/fileUpload/"
            list-type="picture-card"
            :file-list="fileList"
            @preview="handlePreview"
            @change="picHandleChange"
          >
          </a-upload>
          <a-modal :visible="previewVisible" :footer="null" @cancel="handleCancel">
            <img alt="example" style="width: 100%" :src="previewImage" />
          </a-modal>
        </a-col>
      </a-row>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col style="margin-bottom: 15px"><span style="font-size: 15px;font-weight: 650;color: #000c17">订单信息</span></a-col>
        <a-col :span="8"><b>订单编号：</b>
          {{ orderData.code ? orderData.code : '- -' }}
        </a-col>
        <a-col :span="8"><b>开始借用时间：</b>
          {{ orderData.startDate ? orderData.startDate : '- -' }}
        </a-col>
        <a-col :span="8"><b>归还时间：</b>
          {{ orderData.endDate ? orderData.endDate : '- -' }}
        </a-col>
      </a-row>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col :span="8"><b>租借小时：</b>
          {{ orderData.rentHour ? orderData.rentHour : '- -' }}
        </a-col>
        <a-col :span="8"><b>单价（元）：</b>
          {{ orderData.unitPrice ? orderData.unitPrice : '- -' }}
        </a-col>
        <a-col :span="8"><b>押金（元）：</b>
          {{ orderData.depositPrice ? orderData.depositPrice : '- -' }}
        </a-col>
      </a-row>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col :span="8"><b>总价格：</b>
          {{ orderData.totalPrice ? (orderData.totalPrice + '元') : '- -' }}
        </a-col>
        <a-col :span="8"><b>归还时间：</b>
          {{ orderData.returnDate ?orderData.returnDate : '- -' }}
        </a-col>
        <a-col :span="8"><b>下单时间：</b>
          {{ orderData.createDate ?orderData.createDate : '- -' }}
        </a-col>
      </a-row>
      <br/>
    </div>
  </a-modal>
</template>

<script>
import baiduMap from '@/utils/map/baiduMap'
import moment from 'moment'
moment.locale('zh-cn')
function getBase64 (file) {
  return new Promise((resolve, reject) => {
    const reader = new FileReader()
    reader.readAsDataURL(file)
    reader.onload = () => resolve(reader.result)
    reader.onerror = error => reject(error)
  })
}
export default {
  name: 'orderView',
  props: {
    orderShow: {
      type: Boolean,
      default: false
    },
    orderData: {
      type: Object
    }
  },
  computed: {
    show: {
      get: function () {
        return this.orderShow
      },
      set: function () {
      }
    }
  },
  data () {
    return {
      loading: false,
      fileList: [],
      previewVisible: false,
      previewImage: '',
      repairInfo: null,
      reserveInfo: null,
      durgList: [],
      logisticsList: [],
      current: 0,
      userInfo: null,
      orderInfo: null,
      deviceInfo: null
    }
  },
  watch: {
    orderShow: function (value) {
      if (value) {
        this.current = this.orderData.status
        if (this.orderData.typeImages) {
          this.imagesInit(this.orderData.typeImages)
        }
        this.queryOrderDetail(this.orderData.code)
      }
    }
  },
  methods: {
    queryOrderDetail (orderCode) {
      this.$get(`/cos/rent-order-info/queryOrderDetail/${orderCode}`).then((r) => {
        this.orderInfo = r.data.orderInfo
        this.userInfo = r.data.userInfo
        this.deviceInfo = r.data.deviceInfo
      })
    },
    overOrder () {
      this.loading = true
      this.$get(`/cos/rent-order-info/checkReturnDevice`, {
        orderCode: this.orderData.code
      }).then((r) => {
        this.loading = false
        this.$emit('success')
      })
    },
    local (orderData) {
      baiduMap.clearOverlays()
      baiduMap.rMap().enableScrollWheelZoom(true)
      // eslint-disable-next-line no-undef
      let point = new BMap.Point(orderData.longitude, orderData.latitude)
      baiduMap.pointAdd(point)
      baiduMap.findPoint(point, 16)
      // let driving = new BMap.DrivingRoute(baiduMap.rMap(), {renderOptions:{map: baiduMap.rMap(), autoViewport: true}});
      // driving.search(new BMap.Point(this.nowPoint.lng,this.nowPoint.lat), new BMap.Point(scenic.point.split(",")[0],scenic.point.split(",")[1]));
    },
    imagesInit (images) {
      if (images !== null && images !== '') {
        let imageList = []
        images.split(',').forEach((image, index) => {
          imageList.push({uid: index, name: image, status: 'done', url: 'http://127.0.0.1:9527/imagesWeb/' + image})
        })
        this.fileList = imageList
      }
    },
    handleCancel () {
      this.previewVisible = false
    },
    async handlePreview (file) {
      if (!file.url && !file.preview) {
        file.preview = await getBase64(file.originFileObj)
      }
      this.previewImage = file.url || file.preview
      this.previewVisible = true
    },
    picHandleChange ({ fileList }) {
      this.fileList = fileList
    },
    onClose () {
      this.$emit('close')
    }
  }
}
</script>

<style scoped>

</style>

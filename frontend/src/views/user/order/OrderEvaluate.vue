<template>
  <a-modal v-model="show" title="订单评价" @cancel="onClose" :width="1000">
    <template slot="footer">
      <a-button key="back" @click="onClose">
        取消
      </a-button>
      <a-button key="submit" type="primary" :loading="loading" @click="handleSubmit">
        提交
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
      <a-row style="padding-left: 24px;padding-right: 24px;" v-if="userInfo != null">
        <a-col style="margin-bottom: 15px"><span style="font-size: 15px;font-weight: 650;color: #000c17">用户信息</span></a-col>
        <a-col :span="8"><b>用户编号：</b>
          {{ userInfo.code ? userInfo.code : '- -' }}
        </a-col>
        <a-col :span="8"><b>用户名称：</b>
          {{ userInfo.name ? userInfo.name : '- -' }}
        </a-col>
        <a-col :span="8"><b>联系方式：</b>
          {{ userInfo.phone }}
        </a-col>
      </a-row>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col style="margin-bottom: 15px"><span style="font-size: 15px;font-weight: 650;color: #000c17">器材信息</span></a-col>
        <a-col :span="8"><b>器材名称：</b>
          {{ deviceInfo.name ? deviceInfo.name : '- -' }}
        </a-col>
        <a-col :span="8"><b>型号：</b>
          {{ deviceInfo.model ? deviceInfo.model : '- -' }}
        </a-col>
        <a-col :span="8"><b>品牌：</b>
          {{ deviceInfo.brand ? deviceInfo.brand : '- -' }}
        </a-col>
      </a-row>
      <br/>
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
    <a-form :form="form" layout="vertical" style="padding: 20px">
      <a-row :gutter="20">
        <a-col :span="12">
          <a-form-item label='评价分数' v-bind="formItemLayout">
            <a-rate v-decorator="[
            'score',
            { rules: [{ required: true, message: '请输入评价分数!' }] }
            ]" />
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label='评价内容' v-bind="formItemLayout">
            <a-textarea :rows="6" v-decorator="[
            'content',
             { rules: [{ required: true, message: '请输入评价内容!' }] }
            ]"/>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label='图册' v-bind="formItemLayout">
            <a-upload
              name="avatar"
              action="http://127.0.0.1:9527/file/fileUpload/"
              list-type="picture-card"
              :file-list="fileList"
              @preview="handlePreview"
              @change="picHandleChange"
            >
              <div v-if="fileList.length < 8">
                <a-icon type="plus" />
                <div class="ant-upload-text">
                  Upload
                </div>
              </div>
            </a-upload>
            <a-modal :visible="previewVisible" :footer="null" @cancel="handleCancel">
              <img alt="example" style="width: 100%" :src="previewImage" />
            </a-modal>
          </a-form-item>
        </a-col>
      </a-row>
    </a-form>
  </a-modal>
</template>

<script>
import {mapState} from 'vuex'
function getBase64 (file) {
  return new Promise((resolve, reject) => {
    const reader = new FileReader()
    reader.readAsDataURL(file)
    reader.onload = () => resolve(reader.result)
    reader.onerror = error => reject(error)
  })
}
const formItemLayout = {
  labelCol: { span: 24 },
  wrapperCol: { span: 24 }
}
export default {
  name: 'evaluateAdd',
  props: {
    evaluateAddVisiable: {
      default: false
    },
    orderData: {
      type: Object
    }
  },
  computed: {
    ...mapState({
      currentUser: state => state.account.user
    }),
    show: {
      get: function () {
        return this.evaluateAddVisiable
      },
      set: function () {
      }
    },
    columns () {
      return [{
        title: '名称',
        dataIndex: 'dishesName'
      }, {
        title: '图片',
        dataIndex: 'images',
        customRender: (text, record, index) => {
          if (!record.images) return <a-avatar shape="square" icon="user" />
          return <a-popover>
            <template slot="content">
              <a-avatar shape="square" size={132} icon="user" src={ 'http://127.0.0.1:9527/imagesWeb/' + record.images.split(',')[0] } />
            </template>
            <a-avatar shape="square" icon="user" src={ 'http://127.0.0.1:9527/imagesWeb/' + record.images.split(',')[0] } />
          </a-popover>
        }
      }, {
        title: '购买数量',
        dataIndex: 'amount'
      }, {
        title: '单价',
        dataIndex: 'unitPrice'
      }, {
        title: '总价格',
        dataIndex: 'totalPrice',
        customRender: (text, row, index) => {
          if (text !== null) {
            return text
          } else {
            return '- -'
          }
        }
      }]
    }
  },
  data () {
    return {
      formItemLayout,
      form: this.$form.createForm(this),
      loading: false,
      fileList: [],
      previewVisible: false,
      previewImage: '',
      durgList: [],
      logisticsList: [],
      current: 0,
      buyer: null,
      sale: null,
      commodityData: null,
      userInfo: null,
      orderInfo: null,
      merchantInfo: null,
      orderItemInfo: [],
      addressInfo: null,
      staffInfo: null,
      deviceInfo: null,
      evaluateInfo: null
    }
  },
  watch: {
    evaluateAddVisiable: function (value) {
      if (value) {
        this.current = this.orderData.status
        this.queryOrderDetail(this.orderData.code)
      }
    }
  },
  methods: {
    queryOrderDetail(orderCode) {
      this.$get(`/cos/rent-order-info/queryOrderDetail/${orderCode}`).then((r) => {
        this.orderInfo = r.data.orderInfo
        this.userInfo = r.data.userInfo
        this.deviceInfo = r.data.deviceInfo
      })
    },
    dataInit (orderId) {
      this.$get(`/cos/order-info/${orderId}`).then((r) => {
        this.userInfo = r.data.user
        this.orderInfo = r.data.order
        this.merchantInfo = r.data.merchant
        this.orderItemInfo = r.data.orderItem
        this.addressInfo = r.data.address
        this.staffInfo = r.data.staff
        this.evaluateInfo = r.data.evaluate
      })
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
    reset () {
      this.loading = false
      this.form.resetFields()
    },
    onClose () {
      this.reset()
      this.$emit('close')
    },
    handleSubmit () {
      // 获取图片List
      let images = []
      this.fileList.forEach(image => {
        images.push(image.response)
      })
      this.form.validateFields((err, values) => {
        values.orderCode = this.orderData.code
        values.userId = this.orderData.userId
        values.images = images.length > 0 ? images.join(',') : null
        if (!err) {
          this.loading = true
          this.$post('/cos/evaluate-info', {
            ...values
          }).then((r) => {
            this.reset()
            this.$emit('success')
          }).catch(() => {
            this.loading = false
          })
        }
      })
    }
  }
}
</script>

<style scoped>

</style>

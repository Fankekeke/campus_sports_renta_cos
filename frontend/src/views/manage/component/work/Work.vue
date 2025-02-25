<template>
  <a-card :bordered="false" class="card-area">
    <div style="width: 100%">
      <a-col :span="22" v-if="newsList.length > 0">
        <a-alert
          banner
          :message="newsContent"
          type="info"
        />
      </a-col>
      <a-col :span="2">
        <a-button type="primary" style="margin-top: 2px;margin-left: 10px" @click="newsNext">下一页</a-button>
      </a-col>
    </div>
    <br/>
    <br/>
    <a-row style="margin-top: 15px">
      <a-col :span="24">
        <a-card hoverable :loading="loading" :bordered="false" title="公告信息" style="margin-top: 15px">
          <div style="padding: 0 22px">
            <a-list item-layout="vertical" :pagination="pagination" :data-source="newsList">
              <a-list-item slot="renderItem" key="item.title" slot-scope="item, index">
                <template slot="actions">
              <span key="message">
                <a-icon type="message" style="margin-right: 8px" />
                {{ item.date }}
              </span>
                </template>
                <a-list-item-meta :description="item.content" style="font-size: 14px">
                  <a slot="title">{{ item.title }}</a>
                </a-list-item-meta>
              </a-list-item>
            </a-list>
          </div>
        </a-card>
      </a-col>
    </a-row>
    <a-row :gutter="30" v-if="userInfo != null">
      <a-col :span="6">
        <a-card :bordered="false">
          <span slot="title">
            <a-icon type="user" style="margin-right: 10px" />
            <span>用户信息</span>
          </span>
          <div>
            <a-avatar :src="'http://127.0.0.1:9527/imagesWeb/' + userInfo.images" shape="square" style="width: 100px;height: 100px;float: left;margin: 10px 0 10px 10px" />
            <div style="float: left;margin-left: 20px;margin-top: 8px">
              <span style="font-size: 20px;font-family: SimHei">{{ userInfo.name }}</span>
              <span style="font-size: 14px;font-family: SimHei">{{ userInfo.code }}</span>
            </div>
            <div style="float: left;margin-left: 20px;margin-top: 8px">
              <span style="font-size: 14px;font-family: SimHei">电话：{{ userInfo.phone == null ? '- -' : userInfo.phone }}</span>
            </div>
            <div style="float: left;margin-left: 20px;margin-top: 8px">
              <span style="font-size: 14px;font-family: SimHei">邮箱：{{ userInfo.email == null ? '- -' : userInfo.email }}</span>
            </div>
          </div>
        </a-card>
      </a-col>
    </a-row>
    <div style="background:#ECECEC; padding:30px;margin-top: 30px;margin-bottom: 30px">
      <a-radio-group button-style="solid" v-model="checkFlag" style="width: 100%">
        <a-radio-button :value="item.id" style="text-align: center" v-for="(item, index) in consumableType" :key="index">
          item.name
        </a-radio-button>
      </a-radio-group>
      <a-row :gutter="30">
        <a-col :span="4" v-for="(item, index) in statusList" :key="index">
          <div style="background: #f8f8f8">
            <a-carousel autoplay style="height: 150px;" v-if="item.typeImages !== undefined && item.typeImages !== ''">
              <div style="width: 100%;height: 150px" v-for="(item, index) in item.typeImages.split(',')" :key="index">
                <img :src="'http://127.0.0.1:9527/imagesWeb/'+item" style="width: 100%;height: 150px">
              </div>
            </a-carousel>
            <div style="padding: 10px">
              <div>{{ item.name }} - {{ item.brand }} </div>
              <div style="margin-top: 10px;font-size: 13px;">
                <a-row>
                  <a-col :span="12">
                    单价：<span style="color: #fa541c">{{ item.unitPrice }}元/小时</span>
                  </a-col>
                  <a-col :span="12">
                    押金：<span style="color: #fa541c">{{ item.depositPrice }}元</span>
                  </a-col>
                </a-row>
              </div>
              <div style="margin-top: 10px">
                <a-row :gutter="8">
                  <a-col :span="24">
                    <a-tag size="mini" color="blue">{{ item.typeName }}</a-tag>
                    <span style="font-size: 13px;margin-left: 10px">{{ item.model }}</span>
                  </a-col>
                  <a-col :span="24"><a-button type="primary" style="width: 100%;margin-top: 15px" @click="showModal(item)">下单</a-button></a-col>
                </a-row>
              </div>
            </div>
          </div>
        </a-col>
      </a-row>
      <a-modal
        title="选择租借器械"
        :visible="visible"
        @ok="reserveSpace"
        @cancel="handleCancel"
      >
        <a-form :form="form" layout="vertical">
          <a-row :gutter="20">
            <a-col :span="24" v-if="spaceInfo != null">
              <div style="padding: 10px">
                <a-row :gutter="8">
                  <a-col :span="12">
                    <div>{{ spaceInfo.name }} - {{ spaceInfo.brand }} </div>
                  </a-col>
                  <a-col :span="12">
                    <a-tag size="mini" color="blue">{{ spaceInfo.typeName }}</a-tag>
                    <span style="font-size: 13px;margin-left: 10px">{{ spaceInfo.model }}</span>
                  </a-col>
                </a-row>
                <div style="margin-top: 10px;font-size: 13px;">
                  <a-row>
                    <a-col :span="12">
                      单价：<span style="color: #fa541c">{{ spaceInfo.unitPrice }}元/小时</span>
                    </a-col>
                    <a-col :span="12">
                      押金：<span style="color: #fa541c">{{ spaceInfo.depositPrice }}元</span>
                    </a-col>
                  </a-row>
                </div>
              </div>
            </a-col>
            <a-col :span="24" style="margin-top: 20px">
              <a-form-item label='租借结束时间' v-bind="formItemLayout">
                <a-date-picker @change="onChange" show-time
                               format="YYYY-MM-DD HH:mm:ss" style="width: 100%" v-decorator="[
                'endDate',
                { rules: [{ required: true, message: '请输入租借结束时间!' }] }
                ]"/>
              </a-form-item>
            </a-col>
            <a-col :span="24" style="margin-top: 20px" v-if="orderInfo != null">
              <a-row>
                <a-col :span="8">
                  租用小时：<span style="color: #fa541c">{{ orderInfo.rentHour }}小时</span>
                </a-col>
                <a-col :span="8">
                  单价：<span style="color: #fa541c">{{ orderInfo.unitPrice }}小时/元</span>
                </a-col>
                <a-col :span="8">
                  支付押金：<span style="color: #fa541c">{{ orderInfo.depositPrice }}元</span>
                </a-col>
                <a-col :span="24" style="margin-top: 10px">
                  <span style="font-size: 16px;font-weight: 600">总价</span>：<span style="color: #fa541c">{{ orderInfo.totalPrice }}元</span>
                </a-col>
              </a-row>
            </a-col>
          </a-row>
        </a-form>
      </a-modal>
    </div>
  </a-card>
</template>

<script>
import {mapState} from 'vuex'

const formItemLayout = {
  labelCol: { span: 24 },
  wrapperCol: { span: 24 }
}
export default {
  name: 'Work',
  data () {
    return {
      form: this.$form.createForm(this),
      pagination: {
        onChange: page => {
          console.log(page)
        },
        pageSize: 3
      },
      formItemLayout,
      visible: false,
      checkFlag: 0,
      statusList: [],
      currentDataList: [],
      bulletinList: [],
      vehicleList: [],
      consumableType: [],
      loading: false,
      userInfo: null,
      memberInfo: null,
      spaceInfo: null,
      orderInfo: null,
      newsList: []
    }
  },
  watch: {
    checkFlag: function (value) {
      this.currentTab = value
      this.tabChange(value)
    }
  },
  computed: {
    ...mapState({
      currentUser: state => state.account.user
    })
  },
  mounted () {
    this.getWorkStatusList()
    this.selectMemberByUserId()
    this.getConsumableType()
  },
  methods: {
    tabChange (checkFlag) {
      if (this.statusList) {
        let result = this.statusList.filter(item => item.typeId === checkFlag)
        if (result) {
          this.currentDataList = result
        }
      }
    },
    getConsumableType () {
      this.$get('/cos/device-type-info/list').then((r) => {
        this.consumableType = r.data.data
      })
    },
    onChange (date, dateString) {
      console.log(dateString)
      if (dateString) {
        this.calculateOrderPrice(dateString)
      }
    },
    calculateOrderPrice (dateString) {
      let values = {}
      values.userId = this.currentUser.userId
      values.deviceId = this.spaceInfo.id
      values.endDate = dateString
      this.$post('/cos/rent-order-info/calculate', {
        ...values
      }).then((r) => {
        this.orderInfo = r.data.data
      })
    },
    newsNext () {
      if (this.newsPage + 1 === this.newsList.length) {
        this.newsPage = 0
      } else {
        this.newsPage += 1
      }
      this.newsContent = `《${this.newsList[this.newsPage].title}》 ${this.newsList[this.newsPage].content}`
    },
    showModal (row) {
      this.$get(`/cos/rent-order-info/checkOrderByUser`, {userId: this.currentUser.userId}).then((r) => {
        if (r.data.data) {
          this.spaceInfo = row
          this.visible = true
        }
      })
    },
    handleCancel (e) {
      console.log('Clicked cancel button')
      this.orderInfo = null
      this.form.resetFields()
      this.visible = false
    },
    selectVehicleByUserId () {
      this.$get(`/cos/vehicle-info/user/${this.currentUser.userId}`).then((r) => {
        this.vehicleList = r.data.data
      })
    },
    reserveSpace () {
      let data = this.orderInfo
      this.$post('/cos/rent-order-info', data).then((r) => {
        this.$message.success('下单成功！请前往支付')
        // this.visible = false
        // this.getWorkStatusList()
        const divForm = document.getElementsByTagName('div')
        if (divForm.length) {
          document.body.removeChild(divForm[0])
        }
        const div = document.createElement('div')
        console.log(r.data.msg)
        div.innerHTML = r.data.msg // data就是接口返回的form 表单字符串
        // console.log(div.innerHTML)
        document.body.appendChild(div)
        document.forms[0].setAttribute('target', '_self') // 新开窗口跳转
        document.forms[0].submit()
      })
    },
    selectMemberByUserId () {
      this.$get(`/cos/user-info/selectMemberByUserId/${this.currentUser.userId}`).then((r) => {
        this.userInfo = r.data.user
        this.newsList = r.data.bulletin
        if (this.newsList.length !== 0) {
          this.newsContent = `《${this.newsList[0].title}》 ${this.newsList[0].content}`
        }
      })
    },
    getWorkStatusList () {
      this.$get(`/cos/device-info//queryOnlineDevice`).then((r) => {
        this.statusList = r.data.data
        this.currentDataList = r.data.data
      })
    }
  }
}
</script>

<style scoped>
>>> .ant-card-meta-title {
  font-size: 13px;
  font-family: SimHei;
}
>>> .ant-card-meta-description {
  font-size: 12px;
  font-family: SimHei;
}
>>> .ant-divider-with-text-left {
  margin: 0;
}

>>> .ant-card-head-title {
  font-size: 13px;
  font-family: SimHei;
}
>>> .ant-card-extra {
  font-size: 13px;
  font-family: SimHei;
}
.ant-carousel >>> .slick-slide {
  text-align: center;
  height: 250px;
  line-height: 250px;
  overflow: hidden;
}

</style>

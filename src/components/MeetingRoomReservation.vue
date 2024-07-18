<template>
  <div class="meeting-room-reservation">
    <el-dropdown trigger="click" @command="handleDropdownCommand">
      <div class="user-info">
        <div class="user-profile">
          <el-avatar :src="userInfo.avatar" :size="40" shape="circle"></el-avatar>
          <span class="nickname">{{ userInfo.nickname }}</span>
        </div>
        <i class="el-icon-arrow-down el-icon--right"></i> <!-- 添加向下的小箭头图标 -->
      </div>
      <!-- 下拉选项 -->
      <el-dropdown-menu slot="dropdown">
        <el-dropdown-item command="login" v-if="!isLoggedIn">登录</el-dropdown-item>
        <el-dropdown-item command="logout" v-if="isLoggedIn">安全退出</el-dropdown-item>
      </el-dropdown-menu>
    </el-dropdown>
    <div class="title">科蓝会议室预约系统</div>
  <template>
</template>
    <div class="reservation-section">
      <div class="time-picker">
    <el-date-picker
      v-model="startDate"
      type="date"
      clearable
      value-format="yyyy-MM-dd 00:00:00"
      placeholder="开始时间"
    ></el-date-picker>
    <span class="divider">至</span>
    <el-date-picker
      v-model="endDate"
      type="date"
      clearable
      value-format="yyyy-MM-dd 23:59:59"
      placeholder="结束时间"
    ></el-date-picker>
  </div>
      <div class="room-select">
        会议室：<el-select v-model="selectedRoom"  placeholder="选择会议室">
          <el-option
            v-for="room in meetingRooms"
            :key="room.value"
            :label="room.label"
            :value="room.value"
          ></el-option>
        </el-select>
      </div>
      <div class="buttons">
        &nbsp;&nbsp;&nbsp;&nbsp;
        <el-button type="primary" @click="queryMeeting">查询</el-button>
        &nbsp;&nbsp;
        <el-button type="success" @click="showBookingModal = true">预约</el-button>
      </div>
      <div class="approval-button">
        <el-button type="warning" @click="goToLogin">审批</el-button>
      </div>
    </div>
    <el-table :data="displayedTableData" style="margin-top: 20px;">
    <el-table-column label="会议室" prop="meetingRoom"></el-table-column>
    <el-table-column label="预约日期" prop="date"></el-table-column>
    <el-table-column label="预约时间" prop="startEndTime"></el-table-column>
    <el-table-column label="预约人" prop="bookerName"></el-table-column>
    <el-table-column label="主要参会人员" prop="memberPro"></el-table-column>
    <el-table-column label="会议主题" prop="meetingTheme"></el-table-column>
    <el-table-column label="预约人邮箱" prop="bookerEmail"></el-table-column>
    <el-table-column label="修改预约人邮箱" ><template slot-scope="{ row }"><el-button type="primary" @click="showUpdateEmail(row)">修改</el-button></template></el-table-column>
    <el-table-column label="操作">
      <template slot-scope="{ row }">
        <el-button :disabled="!clickButton" v-if="row.bookingStatus === 1" type="warning" size="mini" >取消预约待审批</el-button>
        <el-button v-else-if="isBookingInProgress(row)" type="primary" size="mini" disabled>进行中</el-button>
        <el-button v-else :type="hasBookingExpired(row) ? 'info' : 'danger'" 
        :disabled="hasBookingExpired(row)" 
        size="mini" @click="showCancelDialog(row)" > {{ hasBookingExpired(row) ? '已过期' : '取消预约' }}
        </el-button>
      </template>
    </el-table-column>
  </el-table>
  <el-pagination
  :current-page="currentPage"
  :page-sizes="[10, 20, 30]" 
  :page-size="pageSize"
  layout="total, sizes, prev, pager, next, jumper"
  :total="tableData.length"
  @size-change="handleSizeChange"
  @current-change="handleCurrentChange"
/>

   <!-- 验证码弹窗 -->
   <el-dialog title="取消预约确认" :visible.sync="showVerify" 
   width="30%" center > 
   <div> <p>确认取消以下预约？</p > 
    <p>会议室：{{ selectedBooking.meetingRoom }}</p > 
      <p>预约日期：{{ selectedBooking.date }}</p > 
        <p>预约时间：{{ selectedBooking.startEndTime }}</p > 
          <p>预约人：{{ selectedBooking.bookerName }}</p > 
            <el-input v-model="enteredVerificationCode" placeholder="请输入验证码" style="margin-top: 10px;" >
            </el-input> <el-button type="primary" @click="cancelBooking">确认取消</el-button> 
            <el-button @click="closeCancelDialog">取消</el-button> </div> </el-dialog>
<!-- 邮箱弹窗 -->
 <el-dialog title="取消预约确认" :visible.sync="showEmail" 
 width="30%" center > 
 <div> <p>确认修改以下预约会议的邮箱？</p > 
  <p>会议室：{{ selectedBooking.meetingRoom }}</p > 
   <p>预约日期：{{ selectedBooking.date }}</p > 
    <p>预约时间：{{ selectedBooking.startEndTime }}</p > 
     <p>预约人：{{ selectedBooking.bookerName }}</p > 
   <p>会议室邮箱：{{ selectedBooking.bookerEmail }}</p > 
    <el-input v-model="updateEmail" placeholder="请输入修改的邮箱" style="margin-top: 10px;" >
    </el-input> <el-button type="primary" @click="cancelEmail">确认取消</el-button> 
    <el-button @click="closeCancelEmail">取消</el-button> </div> </el-dialog>
    <div class="modal fade" v-show="showBookingModal">
   <!-- 预约弹窗 -->
  <el-dialog :visible.sync="showBookingModal" title="预约会议室" :modal-append-to-body="false" center @close="resetForm">
    <div class="booking-form">
      <!-- 选择预约时间：开始时间和结束时间放在一行 -->
      <div class="form-item">
        <span class="label"><span class="required">*</span>选择预约时间：</span>
        <div style = "90%">
        <el-date-picker
        style="width: 40%;"
          v-model="bookingStartTime"
          type="datetime"
          clearable
          format="yyyy-MM-dd HH:mm"
          value-format="yyyy-MM-dd HH:mm:01"
          :picker-options="pickerOptions"
          placeholder="开始时间"
        ></el-date-picker>
        <span class="divider">至</span>
        <el-date-picker
        style="width: 40%;"
          v-model="bookingEndTime"
          type="datetime"
          clearable
          format="yyyy-MM-dd HH:mm"
          value-format="yyyy-MM-dd HH:mm:00"
          :picker-options="pickerOptions"
          placeholder="结束时间"
        ></el-date-picker>
      </div>
      </div>
      <br>
      <!-- 输入预约人、会议主题、预约人邮箱 -->
      <div class="form-item">
        <div class="form-row">
        <span class="label"><span class="required">*</span>预约人：</span>
        <input class="inputform" v-model="bookingPerson" placeholder="请输入预约人">
      </div>
      <div class="form-row">
        <span class="label"><span class="required">*</span>主要参会人员：</span>
        <input class="inputform" v-model="memberPro" placeholder="请输入主要参会人员">
      </div>
      <div class="form-row">
        <span class="label"><span class="required">*</span>会议主题：</span>
        <input class="inputform" v-model="bookingTheme" placeholder="请输入会议主题">
      </div>
      <div class="form-row">
        <span class="label"><span class="required">*</span>预约人邮箱：</span>
        <input class="inputform" v-model="bookingAction" placeholder="请输入预约人邮箱">
      </div>
        <span v-if="isInvalidEmail" class="error-text">邮箱格式:xxxx@csii.com.cn</span>
      </div>

      <!-- 下拉框选择会议室 -->
      <div class="form-item">
        <span class="label" > <span class="required">*</span>会议室：</span>
        <el-select v-model="bookingRoom" placeholder="选择会议室">
          <el-option
          v-for="room in meetingRooms" 
          :key="room.value" 
          :label="room.label" 
          :value="room.value"
          ></el-option>
        </el-select>
      </div>

      <!-- 预约和取消按钮 -->
      <div class="form-item btn-group">
        <el-button @click="closeBookingModal">取消</el-button>
        <el-button type="primary" @click="addBooking">预约</el-button>
      </div>
    </div>
  </el-dialog>
</div>
  </div>
</template>

<script>
import axios from 'axios';
import { Message } from 'element-ui';
export default {
  data() {
    return {
      pickerOptions: {
       disabledDate: this.disabledDate,
       selectableRange: this.getSelectableRange(),
      },
      userInfo: {
        avatar: require('@/assets/userLogo.jpeg'),     // 从登录状态中获取头像URL
        nickname: '游客',   // 从登录状态中获取昵称
      },
      tableData:[],
      startDate: '',
      endDate: '',
      selectedRoom: '',
      selectedRoomLabel:'',
      addedRooms: [],
      clickButton:false,
      currentPage: 1, // 当前页码 
      pageSize: 10, // 每页显示条数
      meetingRooms: [
      { value: '', label: '请选择' },
      { value: '会议室2', label: '会议室2' },
      { value: '会议室3', label: '会议室3' },
      { value: '会议室6', label: '会议室6' }, // 添加会议室6
      { value: '会议室9', label: '会议室9' }, // 添加会议室9
      { value: '临时会议室1', label: '临时会议室1' }, // 添加临时会议室
      { value: '临时会议室2', label: '临时会议室2' }, // 添加临时会议室
    ],
    showTimeConflict: false,
    selectedDate: null,
    showBookingModal: false,
    bookingPerson: '',
    bookingRoom: '',
    bookingTheme: '',
    bookingAction: '',
    memberPro: '',
    bookingStartTime: null,
    bookingEndTime: null,
    dataRange: 7,
    startToday: true,
    showDropdown: false,
    maxBookingsLength: 0,
    isLoggedIn: false,
    showVerify: false,
    selectedBooking: '', 
    enteredVerificationCode: '', 
    updateEmail:'',
    showEmail: false,
    currentURL: '',
    };
  },
  created() {
    this.currentURL = window.location.href;
    this.currentURL = this.currentURL.substring(0,this.currentURL.lastIndexOf("#/"));
    // 在主页加载时检查是否存在 token
    const token = localStorage.getItem('token');
    if (token) {
      // 如果存在 token，则向后端请求用户信息
      this.updateUserInfo();
      this.isLoggedIn = true;
    }
    const saveTimer = localStorage.getItem('tokenExpiration');
    if(saveTimer){
      setTimeout(() => {
        this.logout();
      }, 15 * 60 * 1000);
    }
  },
  mounted() {
    axios.post(this.currentURL+'getRecentBookRecords')
        .then(response => {
          const allList = response.data.dataList[0][0].concat(response.data.dataList[0][1],response.data.dataList[0][2],response.data.dataList[0][3],response.data.dataList[0][4],response.data.dataList[0][5]);
          this.tableData = allList;
        })
        .catch(error => {
          // 获取失败的处理逻辑
          console.error('获取失败：', error.message);
        });
  },
  computed: {
    displayedTableData() { 
      const startIndex = (this.currentPage - 1) * this.pageSize; 
      const endIndex = startIndex + this.pageSize; 
      return this.tableData.slice(startIndex, endIndex); },
  },
  methods: {
      isBookingInProgress(booking) {
      const currentTimestamp = new Date().getTime();
      const bookingStartTimestamp = new Date(`${booking.date} ${booking.startEndTime.split('-')[0]}`).getTime();
      const bookingEndTimestamp = new Date(`${booking.date} ${booking.startEndTime.split('-')[1]}`).getTime();
      
      // 新状态 "进行中" 的判断条件
      const isInProgress = currentTimestamp >= bookingStartTimestamp && currentTimestamp <= bookingEndTimestamp;
      // 返回新状态的判断结果
      return isInProgress;
    },
    closeCancelEmail(){
      this.showEmail = false;
      this.selectedBooking = "";
    },
    showUpdateEmail(booking){
 this.selectedBooking = booking;
 this.showEmail = true;
 },
 cancelEmail(){
  // 这里可以校验验证码是否正确，如果正确则执行取消预约操作 
  const Formdata = new FormData();
  Formdata.append('recordId', this.selectedBooking.recordId);
  Formdata.append('bookerEmail',this.updateEmail)
  console.log(this.selectedBooking.recordId);
  console.log(this.updateEmail);
  console.log(Formdata.recordId);
  axios.post(this.currentURL+'updateEmail', Formdata)
   .then(response => {
    if(response.data == true){
      Message.success('修改邮箱提交成功!');
     window.location.reload();
     this.showEmail = false;
    }else{
  alert("修改邮箱申请失败");
   }
  })
  .catch(error => {
  // 取消失败的处理逻辑
  console.error('取消失败：', error.message);
  this.flag = true;
  });
 },
    cancelBooking() { 
      // 这里可以校验验证码是否正确，如果正确则执行取消预约操作 
      const Record = new FormData();
      Record.append('recordId', this.selectedBooking.recordId);
      Record.append('randomString',this.enteredVerificationCode)
      axios.post(this.currentURL+'cancel', Record)
      .then(response => {
        if(response.data == true){
          window.location.reload();
          Message.success('取消预约申请提交成功!');
          this.showVerify = false;
        }else{
          alert("取消预约申请失败");
        }
      })
      .catch(error => {
        // 取消失败的处理逻辑
        console.error('取消失败：', error.message);
      });
    },
    closeCancelDialog() { 
      this.showVerify = false; 
      this.selectedBooking = ''; 
      this.enteredVerificationCode = ''; 
    },
    resetForm(){
      this.bookingPerson='',
      this.memberPro='',
      this.bookingTheme='',
      this.bookingAction='',
      this.bookingRoom='',
      this.bookingStartTime='',
      this.bookingEndTime=''
    },
    showCancelDialog(booking) {
      if(this.hasBookingExpired(booking)){
        this.$message.error('当前预约时间已过期')
      }else{
        this.selectedBooking = booking;
       this.showVerify = true; 
      }
    },
    disabledDate(time) {
      const today = new Date();
      today.setHours(0, 0, 0, 0);
      return time.getTime() < today.getTime();
    },
    getSelectableRange() {
      const ranges = [];
      for (let hour = 0; hour < 24; hour++) {
      ranges.push(`${hour}:00:00-${hour}:00:00`);
      ranges.push(`${hour}:30:00-${hour}:30:00`);
      ranges.push(`23:59:00-23:59:00`);
      }
      return ranges;
    },
    handleSizeChange(newSize) { this.pageSize = newSize; }, 
    handleCurrentChange(newPage) { this.currentPage = newPage; },
    hasBookingExpired(booking) { 
      const currentTimestamp = new Date().getTime();
       const bookingStartTimestamp = new Date(`${booking.date} ${booking.startEndTime.split('-')[0]}`).getTime(); 
       return bookingStartTimestamp < currentTimestamp; },
    //登录
    handleDropdownCommand(command) {
      if (command === "login") {
        // 处理登录功能，跳转到登录页面或者显示登录弹窗
        this.goToLogin();
      } else if (command === "logout") {
        // 处理安全退出功能
        // 例如清除用户登录状态，返回到未登录状态等
        this.handleLogout();
      }
      // 隐藏下拉菜单
      this.showDropdown = false;
    },
    goToLogin() {
      // 获取当前的路由路径
      const currentRoutePath = this.$route.path;
      if(!this.isLoggedIn){
        this.$router.push('/login');
      }else{
        this.$router.push('/cancel');
      }
    },
    isInvalidEmail() { 
      // 使用正则表达式检查邮箱格式是否正确 
      const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/; 
      return this.bookingAction && !emailPattern.test(this.bookingAction); }
    ,checkBooking(){
      this.$message.error('正在审批中');
    }
    ,
    logout(){
        // 清除 localStorage 或者 sessionStorage 中的 token
        localStorage.removeItem('token');
        localStorage.removeItem('username');
        localStorage.removeItem('tokenExpiration');
        Message.error("登录过期，请重新登录");
        setTimeout(() => {
          window.location.reload();
        },1000);
        
      },
    handleLogout() {
      // 清除 localStorage 或者 sessionStorage 中的 token
      localStorage.removeItem('token');
      localStorage.removeItem('username');
      Message.error("登录过期，请重新登录");
      // 重置登录状态和用户信息
      this.isLoggedIn = false;
      this.userInfo = {
        avatar: require('@/assets/userLogo.jpeg'),
        nickname: '游客',
      };
    },
    //查询的方法
    queryMeeting() {
      this.currentPage = 1;
      if ((this.startDate !== '' && this.endDate == '') || (this.startDate == '' && this.endDate !== '')) {
      // 提示用户要么都选择日期，要么都不选择日期
      this.$message.error('请选择起始日期和结束日期，或者都不选择');
      return; // 终止查询操作
      }
      if(this.startDate == null){
        this.startDate = '';
      }
      if(this.endDate == null){
        this.endDate = '';
      }
      this.startToday = false;
      const start = new Date(this.startDate);
      const end = new Date(this.endDate);
      if(start > end){
        this.$message.error('开始日期应小于结束日期');
        return;
      }
      const timeDifference = end.getTime() - start.getTime();
      const daysDifference = Math.floor(timeDifference / (1000 * 60 * 60 * 24));
      this.dataRange = daysDifference + 1;
      if(this.dataRange > 20){
        this.$message.error('请查询20天内的数据');
        this.dataRange = 20;
        return;
      }else{
        this.dataRange = daysDifference + 1;
      }
      
      const formData = new FormData();
      if(this.startDate==''){
        formData.append('startTime', this.formatDateToYYYYMMDDHHMMSS(new Date()));
      }else{
        formData.append('startTime', this.startDate);
      }
      formData.append('endTime', this.endDate);
      formData.append('meetingRoom',this.selectedRoom);
      axios.post(this.currentURL+'getBookRecords',formData)
        .then(response => {
            const allList = response.data.dataList[0][0].concat(response.data.dataList[0][1],response.data.dataList[0][2],response.data.dataList[0][3],response.data.dataList[0][4],response.data.dataList[0][5]);
            this.tableData = allList; 
        })
        .catch(error => {
          // 查询失败的处理逻辑
          console.error('查询失败：', error.message);
        });
    },
  //检查预约时间大小
    checkBookingTime(){
      this.selectedDate = new Date().toLocaleDateString();
    const start = new Date(this.bookingStartTime);
    const end = new Date(this.bookingEndTime);
    if (start > end) {
      this.bookingStartTime = '';
      this.bookingEndTime = '';
      this.$message.error('结束时间不能小于起始时间');
      return false;
      }else{}
      return true;
    },
    updateUserInfo() {
      this.userInfo.avatar = require('@/assets/controller.jpg');
      this.userInfo.nickname = localStorage.getItem('username');
    }
    ,
    addBooking(){
      const requestBody = new FormData();
      requestBody.append('startTime', this.bookingStartTime);
      requestBody.append('endTime', this.bookingEndTime);
      requestBody.append('bookerName', this.bookingPerson);
      requestBody.append('meetingTheme', this.bookingTheme);
      requestBody.append('bookerEmail', this.bookingAction);
      requestBody.append('meetingRoom', this.bookingRoom);
      requestBody.append('memberPro',this.memberPro);
      const bookingStartTimeTimestamp = new Date(this.bookingStartTime).getTime();
      const bookingEndTimeTimestamp = new Date(this.bookingEndTime).getTime();

      const currentTimestamp = new Date().getTime();
      const EMAIL_REGEX = /^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Z|a-z]{2,}$/;
      if (!EMAIL_REGEX.test(this.bookingAction)) { this.$message.error('请输入有效的预约人邮箱'); return; }
      if(bookingStartTimeTimestamp <= currentTimestamp){
        this.$message.error('无法预约当前时间之前的时间');
        return;
      }

      const bookingStartTimeDate = new Date(this.bookingStartTime);
      const bookingEndTimeDate = new Date(this.bookingEndTime);

      // Check if the booking is for the same day
      if (
        bookingStartTimeDate.getFullYear() !== bookingEndTimeDate.getFullYear() ||
        bookingStartTimeDate.getMonth() !== bookingEndTimeDate.getMonth() ||
        bookingStartTimeDate.getDate() !== bookingEndTimeDate.getDate()
        ) {
          this.$message.error('预约开始时间和结束时间必须在同一天');
          return;
      }

     if( this.checkBookingTime()&&this.validateBookingInfo()){
      axios
        .post(this.currentURL+'addBookRecord', requestBody) 
        .then(response => {
          if(response.data == ''){
            alert("预约失败,请重新预约");
          }else if (response.data == 'timeError'){
            this.$message.error('预约的开始时间或结束时间与已有预约时间冲突');
          }else {
            alert("预约成功,您的预约码为 : " + response.data + "\n" + "预约信息已发送至您的邮箱，请注意查收！")
            window.location.reload();
          }
          // 预约成功后，关闭弹窗
          this.closeBookingModal();
        })
        .catch(error => {
          console.error('预约出错：', error.message);
        });
     }
    }
    ,
    formatDateToYYYYMMDDHHMMSS(date) { 
      const year = date.getFullYear(); 
      const month = String(date.getMonth() + 1).padStart(2, '0'); 
      const day = String(date.getDate()).padStart(2, '0'); 
      return `${year}-${month}-${day} 00:00:00`; }
    ,isDateMatch(bookingDate, dayOffset) {
      const today = new Date();
      today.setHours(0, 0, 0, 0);
      const targetDate = new Date(today);
      targetDate.setDate(targetDate.getDate() + dayOffset - 1);
      return bookingDate === targetDate.toISOString().split('T')[0];
    },
    //检查信息为空
    validateBookingInfo() {
    if (!this.bookingStartTime || !this.bookingEndTime || !this.bookingPerson || !this.bookingTheme || !this.bookingAction || !this.bookingRoom) {
      this.$message.error('信息不能为空，请填写完整预约信息');
      return false;
    }
    return true;
  },
    getMaxBookingIndex(dayOffset) {
      let maxIndex = 0;
      this.rooms.forEach((room) => {
        room.bookings.forEach((booking) => {
          if (this.isDateMatch(booking.date, dayOffset)) {
            maxIndex = Math.max(maxIndex, room.bookings.filter((b) => this.isDateMatch(b.date, dayOffset)).length);
          }
        });
      });
      return maxIndex;
    },
    openBookingModal() {
    this.showBookingModal = true;
  },
  closeBookingModal() {
    this.showBookingModal = false;
  },
  bookRoom() {
    this.closeBookingModal();
  },// 方法：处理会议室数据并返回表格数据

  }
};
</script>

<style>
/* 在这里添加组件的样式代码，你可以使用Element UI提供的样式类名 */

.meeting-room-reservation {
  padding: 20px;
  font-family: Arial, sans-serif;
  background-color: #f0f2f5;
  border-radius: 20px;
  margin: 0 auto;
  max-width: 1650px; /* 将宽度调整为1500px */
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  padding: 30px;
  box-sizing: border-box;
}

.error-text { color: red; font-size: 12px; }

.title {
  font-size: 36px;
  font-weight: bold;
  color: #2c3e50;
  text-align: center; /* 居中标题文本 */
  margin-bottom: 20px;
}

.reservation-section {
  display: flex;
  align-items: center;
}

.time-picker {
  display: flex;
  align-items: center;
}

.divider {
  margin: 0 10px;
}

.required { color: red; margin-left: 5px; }

.room-select {
  display: flex;
  align-items: center;
  margin-left: 10px; /* 添加左边距 */
  margin-right: 10px; /* 添加右边距 */
}
.el-table .cell.merged-cell {
  text-align: center;
  font-weight: bold;
  background-color: #f5f7fa;
}
.buttons {
  display: flex;
  align-items: center;
  margin-right: 10px; /* 添加右边距 */
}
.el-dialog__wrapper { overflow: hidden; /* 隐藏弹窗外的内容 */ }
.buttons .el-button {
  margin-right: 10px; /* 添加右边距 */
}

/* 预约时间选择器 */
.el-date-picker {
  width: 300px;
}

/* 会议室选择器 */
.el-select {
  width: 150px;
}

.approval-button {
  margin-left: auto; /* 将审批按钮推到最右边 */
}

.buttons .el-button:last-child {
  margin-right: 10px; /* 最后一个按钮没有右边距 */
}

.time-conflict {
  color: red;
  margin-top: 5px;
}

.el-dialog {
top: 0;
}

.booking-form {
width: 100%;
padding: 20px;
box-sizing: border-box;
}

.user-info {
  display: flex;
  align-items: center;
  justify-content: flex-end; /* 将内容向右对齐 */
  cursor: pointer;
}

.nickname {
  margin-left: 10px; /* 调整昵称与头像之间的距离 */
}

.inputform{
  display: block; width: 53%; padding: 10px; font-size: 14px; border: 1px solid #ccc; border-radius: 4px; background-color: #fff; transition: border-color 0.3s, box-shadow 0.3s;
}

.inputform:focus{
  outline: none; border-color: #409eff; box-shadow: 0 0 0 2px rgba(64, 158, 255, 0.2);
}

.el-input {
width: 100%;
margin-top: 5px;
margin-bottom: 5px;
}


.form-row{
  display: flex; /* 使用flex布局 */
  align-items: center; /* 垂直居中对齐 */
  margin: 10px;
}
.label {
display: inline-block;
width: 120px;
}

.btn-group {
margin-top: 20px;
text-align: center;
}

.form-item.el-date-picker {
display: inline-block;
margin-bottom: 5px;
}

.el-date-picker .el-picker-panel_content{
  width: 100%;
}

.el-picker-panel_content{
  margin: 0px;
}

</style>

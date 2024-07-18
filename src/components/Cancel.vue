<template>
  <div class="approval-page">
    <div class="form-container">
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
        <el-dropdown-item command="logout" >安全退出</el-dropdown-item>
      </el-dropdown-menu>
    </el-dropdown>
      <div class="title">管理员审核</div>
      
      <div class="search-form">
        <el-date-picker
          v-model="startDate"
          type="date"
          :picker-options="pickerOptions"
          value-format="yyyy-MM-dd 00:00:00"
          placeholder="开始日期"
        ></el-date-picker>
        <span class="divider">至</span>
        <el-date-picker
          v-model="endDate"
          type="date"
          :picker-options="pickerOptions"
          value-format="yyyy-MM-dd 23:59:59"
          placeholder="结束日期"
        ></el-date-picker>
        会议室：<el-select v-model="selectedRoom" placeholder="选择会议室">
          <el-option
            v-for="room in meetingRooms"
            :key="room.value"
            :label="room.label"
            :value="room.value"
          ></el-option>
        </el-select>
        &nbsp;&nbsp;&nbsp;&nbsp;
        <el-button type="primary" @click="queryMeeting">查询</el-button>
        &nbsp;&nbsp;
        <el-button type="success" @click="approveAll">一键通过</el-button>
        &nbsp;&nbsp;
        <el-button type="success" @click="showBookingModal = true">长期预约</el-button>
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        <el-button type="warning" @click="returnToHomePage">返回主页</el-button>
      </div>

      <el-table
        :data="displayedTableData"
        border
        class="table-bordered custom-table"
        style="width: 100%"
        @selection-change="handleSelectionChange"
      >
        <el-table-column type="selection"  align="center" width="55" ></el-table-column>
        <el-table-column prop="meetingRoom" label="会议室" align="center" width="120"></el-table-column>
        <el-table-column prop="meetingTime" label="会议预定时间" align="center"></el-table-column>
        <el-table-column prop="bookerName" label="预订人" align="center"></el-table-column>
        <el-table-column prop="meetingTheme" label="会议主题" align="center"></el-table-column>
        <el-table-column prop="commitTime" label="提交申请时间" align="center"></el-table-column>
        <el-table-column prop="duringTime" label="会议预定时长" align="center"></el-table-column>
        <el-table-column label="操作" align="center">
          <template slot-scope="{ row }">
            <el-button type="danger" @click="closeApprove(row)">关闭通过</el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination
  :current-page="currentPage"
  :page-sizes="[5, 10, 15]" 
  :page-size="pageSize"
  layout="total, sizes, prev, pager, next, jumper"
  :total="tableData.length"
  @size-change="handleSizeChange"
  @current-change="handleCurrentChange"
/>
<el-dialog :visible.sync="showBookingModal" title="预约会议室" :modal-append-to-body="false" center @close="resetForm">
    <div class="booking-form">
      <!-- 选择预约时间：开始时间和结束时间放在一行 -->
      <div class="form-item">
        <span class="label"><span class="required">*</span>选择预约时间：</span>
        <div style="90%">
        <el-date-picker
        style="width: 40%;"
          v-model="bookingStartTime"
          type="date"
          clearable
          format="yyyy-MM-dd HH:mm"
          value-format="yyyy-MM-dd 00:00:00"
          :picker-options="pickerOptions"
          placeholder="开始时间"
        ></el-date-picker>
        <span class="divider">至</span>
        <el-date-picker
        style="width: 40%;"
          v-model="bookingEndTime"
          type="date"
          clearable
          format="yyyy-MM-dd HH:mm"
          value-format="yyyy-MM-dd 00:00:00"
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
import { relativeTimeRounding } from 'moment';

export default {
  data() {
    return {
      pickerOptions: {
       disabledDate: this.disabledDate
      },
      userInfo: {
        avatar: require('@/assets/controller.jpg'),     // 从登录状态中获取头像URL
        nickname: localStorage.getItem('username'),   // 从登录状态中获取昵称
      },
      isClick:false,
      startDate: '',
      endDate: '',
      selectedRoom: '',
      tableData: [],
      selectedData:[],
      currentPage: 1, // 当前页码 
      pageSize: 5, // 每页显示条数
      meetingRooms: [
        { value: '', label: '请选择' },
        { value: '会议室2', label: '会议室2' },
        { value: '会议室3', label: '会议室3' },
        { value: '会议室6', label: '会议室6' },
        { value: '会议室9', label: '会议室9' },
        { value: '会议室10', label: '会议室10' },
      ],
      currentURL: '',
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
    };
  },
  computed: {
    displayedTableData() { 
      const startIndex = (this.currentPage - 1) * this.pageSize; 
      const endIndex = startIndex + this.pageSize; 
      return this.tableData.slice(startIndex, endIndex); },
  },
  mounted() {
    axios.post(this.currentURL+'getAllCancelRecords')
        .then(response => {
          // console.log(response.data.dataList);
          this.tableData = response.data.dataList[0];
          // 进行其他的登录成功处理逻辑，比如跳转到主页或其他操作
        })
        .catch(error => {
          // 获取失败的处理逻辑
          console.error('获取失败：', error.message);
        });
  },
  created() {
    this.currentURL = window.location.href;
    this.currentURL = this.currentURL.substring(0,this.currentURL.lastIndexOf("#/"));
    // 在主页加载时检查是否存在 token
    const token = localStorage.getItem('token');
    if (token) {
      // 如果存在 token，则向后端请求用户信息
    }else{
      this.$router.push('/meeting');
    }
    const saveTimer = localStorage.getItem('tokenExpiration');
    if(saveTimer){
      setTimeout(() => {
        this.logout();
      }, 15 * 60 * 1000);
    }
  },
  methods: {
    handleSizeChange(newSize) { this.pageSize = newSize; }, 
    handleCurrentChange(newPage) { this.currentPage = newPage; },
    handleDropdownCommand(command) {
      if (command === "logout") {
        // 处理安全退出功能
        // 例如清除用户登录状态，返回到未登录状态等
        this.logout();
      }
      // 隐藏下拉菜单
      this.showDropdown = false;
    },
    returnToHomePage(){
      this.$router.push('/meeting');
    }
    ,
    logout(){
      // 清除 localStorage 或者 sessionStorage 中的 token
      localStorage.removeItem('token');
      localStorage.removeItem('username');
      localStorage.removeItem("tokenExpiration");
      Message.error("登录过期，请重新登录");
      setTimeout(() => {
        this.$router.push('/meeting');
      },1000);
    },
    handleSelectionChange(selection){
      this.isClick = selection.length > 0;
      // console.log(this.isClick);
      this.selectedData = selection.map(item => item); 
      // 如果需要只获取recordId，可以使用item.recordId 
    }
    ,
    queryMeeting() {
      // 根据开始日期、结束日期、选择的会议室等信息，调用后端API查询会议信息
      // 然后将查询结果填充到表格中，更新this.tableData
      // 省略具体实现
      if ((this.startDate !== '' && this.endDate == '') || (this.startDate == '' && this.endDate !== '')) {
        // 提示用户要么都选择日期，要么都不选择日期
        this.$message.error('请选择起始日期和结束日期，或者都不选择');
        window.location.reload();
        return; // 终止查询操作
      }
      const start = new Date(this.startDate);
        const end = new Date(this.endDate);
        if(start > end){
          this.$message.error('开始日期应小于结束日期');
          return;
        }
      if(this.startDate == null){
        this.startDate = '';
      }
      if(this.endDate == null){
        this.endDate = '';
      }
      const formData = new FormData();
      formData.append('startTime', this.startDate);
      formData.append('endTime', this.endDate);
      formData.append('meetingRoom',this.selectedRoom);
      axios.post(this.currentURL+'getCancelRecords',formData)
        .then(response => {
          // console.log(response.data);
          this.tableData = response.data.dataList[0];
        })
        .catch(error => {
          // 查询失败的处理逻辑
          console.error('查询失败：', error.message);
        });
    },
    approveAll() {
      const myList = [];
      if(this.isClick == false){
        this.tableData.forEach((item)=>{
        myList.push(item.recordId);
      })
      }else{
        this.selectedData.forEach(item => { 
          myList.push(item.recordId); 
        });
      }
      axios.post(this.currentURL+'cancelRecords', myList, {
        headers: {
        'Content-Type': 'application/json'
        }
      })
      .then(response => {
        console.log(response.data);
        // 处理响应
        if(response.data == true){
          Message.success('关闭成功!');
          setTimeout(() => {
              window.location.reload();
              },1000);
        }else {
          Message.error('关闭失败!');
          setTimeout(() => {
              window.location.reload();
              },1000);
        }
      })
    },
    disabledDate(time) {
  const today = new Date();
  today.setHours(0, 0, 0, 0);
  return time.getTime() < today.getTime();
 },
    closeApprove(row) {
      // 将选中的会议信息关闭通过，并调用后端API将关闭通过的状态更新到数据库
      const myList = [row.recordId];
      axios.post(this.currentURL+'cancelRecords',myList)
        .then(response => {
          console.log(response.data);
          if(response.data == true){
            Message.success('关闭成功!');
            setTimeout(() => {
              window.location.reload();
              },1000);
          }else {
            Message.error('关闭失败!');
            setTimeout(() => {
              window.location.reload();
              },1000);
          }
        })
    },
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



     if( this.checkBookingTime()&&this.validateBookingInfo()){
      axios
        .post(this.currentURL+'addBookRecords', requestBody) 
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
  },
};
</script>

<style>
/* 全局样式 */
.approval-page {
  font-family: Arial, sans-serif;
  border-radius: 20px;
  margin: 0 auto;
  max-width: 1650px; /* 将宽度调整为1500px */
  padding: 30px;
  box-sizing: border-box;
}

/* 调整容器样式 */
.form-container {
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



/* 表格样式 */
.custom-table {
  width: 100%;
  margin-top: 20px;
}

/* 表单样式 */
.search-form {
  margin-bottom: 20px;
}

/* 标题样式 */
.title {
  font-size: 24px;
  font-weight: bold;
  text-align: center;
  margin-bottom: 20px;
}
</style>

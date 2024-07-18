<!-- AdminLogin.vue -->
<template>
  <div class="admin-login">
    <h2>管理员登录</h2>
      <div>
        <label for="username">用户名:</label>
        <input type="text" id="username" v-model="username" required>
      </div>
      <div>
        <label for="password">密码:</label>
        <input type="password" id="password" v-model="password" required>
      </div>
      <button type="submit" @click="login">登录</button>
  </div>
</template>

<script>
import axios from 'axios';
export default {
  data() {
    return {
      username: '',
      password: '',
      token: '',
      currentURL: '',
    };
  },
  created() {
    this.currentURL = window.location.href;
    this.currentURL = this.currentURL.substring(0,this.currentURL.lastIndexOf("#/"));
  },
  methods: {
    login() {
      // 这里可以添加登录逻辑，比如验证用户名密码是否正确，以及跳转到管理后台
      // 示例中简单地打印登录信息
      const formData = new FormData();
      formData.append('username', this.username);
      formData.append('password', this.password);

      axios.post(this.currentURL+'login', formData)
        .then(response => {
          if(response.data != ''){
              this.token = response.data;
              localStorage.setItem('token',this.token);
              localStorage.setItem('username',this.username);
              // 设置 Token 的过期时间为 15 分钟（以毫秒为单位）
              const expirationTime = new Date().getTime() + 15 * 60 * 1000;
              localStorage.setItem('tokenExpiration', expirationTime);
              this.$router.push('/cancel');
              // 在登录成功后设置一个定时器，在过期时间后清除 Token
              setTimeout(() => {
                Message.error("登录超时，请重新登录");
                this.logout();
              }, 30 * 1000);
              }else{
               alert("用户名或密码错误");
              }
              // 进行其他的登录成功处理逻辑，比如跳转到主页或其他操作
              })
              .catch(error => {
               // 登录失败的处理逻辑
               console.error('登录失败：', error.message);
              });
  
      },
      logout(){
        // 清除 localStorage 或者 sessionStorage 中的 token
        localStorage.removeItem('token');
        localStorage.removeItem('username');
        this.$router.push('/login');
      },  
  }
};
</script>

<style>
.admin-login {
  max-width: 400px;
  margin: 0 auto;
  padding: 40px;
  border: 2px solid #ccc;
  border-radius: 4px;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
}

.admin-login h2 {
  text-align: center;
  margin-bottom: 20px;
}

.admin-login form div {
  margin-bottom: 25px;
}

.admin-login label {
  display: block;
  margin-bottom: 1px;
}

.admin-login input {
  width: 100%;
  padding: 8px;
  border: 2px solid #ccc;
  border-radius: 4px;
}

.admin-login button {
  width: 100%;
  padding: 10px;
  background-color: #007BFF;
  color: #fff;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}
</style>

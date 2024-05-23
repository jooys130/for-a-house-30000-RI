<script setup>
import { useRouter } from "vue-router";

import { storeToRefs } from "pinia";
import { useMemberStore } from "@/stores/member";

const memberStore = useMemberStore();
const { userLogout } = memberStore; 
const {isLogin,userInfo} = storeToRefs(memberStore);

const router = useRouter();

const logout = () => {
  console.log("로그아웃 버튼"); 
  console.log(userInfo.value); 
  userLogout(userInfo.value.userId);
  router.push({ name : "home"});
}

const goBoardView = () => {
  router.push({ name: "board" });
};

const goUserLogin = () => {
  router.push({ name: "user-login" });
};

const goUserJoin = () => {
  router.push({ name: "user-join" });
};

const goMainView = () => {
  router.push({ name: "home" });
};

const goMapSearchView = () => {
  router.push({ name: "mapsrch" });
};

const goKeywordSearchView = () => {
  router.push({ name: "keywordsrch" });
};

const goMyPage = () => {
  router.push({ name : "user-mypage", params : {userid : userInfo.value.userId}})
}
</script>

<template>
  <nav>
    <div id="left">  
      <div style="display: flex; align-items: center;  width: 100%; justify-content: space-around; ">
        <img src="../../assets/img/logo/logo_1.png" alt="(주)전주김밥" class="cursor_logo" @click="goMainView">
        <div class="cursor" @click="goBoardView">공지사항</div>
          <div class="cursor" @click="goKeywordSearchView">이름 기반 검색</div>
        <div class="cursor" @click="goMapSearchView">위치 기반 검색</div>
        <div class="cursor" v-if="isLogin" @click="goMyPage">마이페이지</div>
      </div>
    </div>

    <div id="right">
      <template v-if="!isLogin">
          <button class="size_sm w_round_bar custom_h2" @click="goUserJoin">
            회원가입
          </button>
          <button class="size_sm w_round_bar custom_h2" @click="goUserLogin">
            로그인
          </button>
      </template>
      <template v-if="isLogin">
        <div class="cursor" @click="goMyPage">
          {{ userInfo.name }}
        </div>
        <div>
          님 안녕하세요
        </div>
        <button class="size_sm w_round_bar custom_h2" @click="logout" style="display:flex; align-items:center; justify-content: center;">
          로그아웃
        </button>
      </template>
    </div>
  </nav>
</template>

<style scoped>
nav {
  height: 8vh;
  display: flex;
  justify-content: space-between;
  align-items: center;
  background-color: white;
  box-shadow: 3px 3px 20px 3px lightgray;
  font-family: 'NanumBarun';
}

#left {
  display: flex;
  align-items: center;
  max-height:80px;
  width:30%;
  justify-content: space-between;
}

#right {
  display: flex;
  align-items: center;
  margin-right: 3vw;
}

#nav-item {
  font: 25px hannaAir;
  display: flex;
  align-items: center;
}

button {
  right: 10px;
  top: 50%;
  width : 100px;
}
.cursor {
  cursor: pointer;
  height:80px;
  display:flex;
  align-items: center;
  font-size: 18px;
}
.cursor_logo{
  cursor: pointer;
  width: 160px;
  height: 80px;
}

@font-face{
  font-family: 'NanumBarun';
  font-weight: 800;
  src:url("../../assets/fonts/ttf/NanumBarunGothic-YetHangul.ttf")
}
</style>

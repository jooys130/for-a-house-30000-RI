<script setup>
import { ref } from "vue";
import { storeToRefs } from "pinia";
import { useRouter } from "vue-router";
import { useMemberStore } from "@/stores/member";

const router = useRouter();

const memberStore = useMemberStore();

const { isLogin } = storeToRefs(memberStore);
const { userLogin, getUserInfo } = memberStore;

const loginUser = ref({
  userId: "",
  password: "",
});

const login = async () => {
    await userLogin(loginUser.value);
    let token = sessionStorage.getItem("accessToken");
    console.log(isLogin);
    if (isLogin) {
    getUserInfo(token);
  }
  router.push({ name : "home"});
};
</script>

<template>
   <v-card class="mx-auto px-6 py-8" style="width: 30vw; margin-top: 200px;" id="login-form" :elevation="17">
        <h1 class="custom_h1">로그인</h1>
        <div style="width: 70%; margin: 0 auto; padding-bottom: 3px;">
            <v-text-field label="아이디" v-model="loginUser.userId"></v-text-field>
            <v-text-field label="비밀번호" :type="visible ? 'text' : 'password'" v-model="loginUser.password"></v-text-field>
            <v-btn
                block
                class="mb-8"
                color="blue-darken-4"
                size="large"
                variant="tonal"
                @click="login"
            >
                LogIn
            </v-btn>
        </div>
    </v-card>
</template>

<style scoped>
#login-form{
    background-color: #f0f6ff;
}


/* #registform{
    background-color: rgba(103, 141, 158, 0.5);
    width : 30vw;
    height : 40vh; 
    border-radius: 5%;
    display: flex;
    flex-direction: column;
} */

/* 로그인 글자 스타일 적용 */
h1{
    font-family: gMedium;
    text-align: center;
}

#login-form{
    width: 60%;
    display: flex;
    flex-direction: column;
    margin: auto;
    font-family: gMedium;
    font-size: 20px;
}

form label{
    margin-top: 10px;
}

/* button{
    width : 200px;
    height : 50px;
    font-size: 20px;
    margin : 0 auto;
    margin-top: 30px;
    background-color: rgb(80, 109, 122);
} */
</style>
<script setup>
import { ref } from 'vue'; 
import { useMemberStore } from "@/stores/member";
import { useRouter } from "vue-router";

const router = useRouter(); 
const memberStore = useMemberStore();
const { userRegist } = memberStore;


const user = ref({
    userId: "", 
    password: "", 
    passwordValid: "", 
    name: "", 
    emailAccount: "", 
    emailDomain: "", 
}); 


const lenRules = [
    (value) => {
        if (value) return true;
        return "반드시 작성해야합니다.";
    },
    (value) => {
        if (value.length >= 4 && value.length <= 16) return true;
        return "4자 이상 16자 이하로 작성해야합니다.";
    },
];

const chkRules = [
    (value) => {
        if (value == user.value.password) return true;
        return "비밀번호가 다릅니다.";
    },
];

const rules = [
    (value) => {
        if (value) return true;
        return "반드시 작성해야합니다.";
    },
];

// Validation 확인 및 로그인 로직 처리
const regist = async () => {
    const registUser = user.value;
    console.log(registUser);
    if (registUser.userId == '' || registUser.password == '' || registUser.name == ''
        || registUser.emailAccount == '' || registUser.emailDomain == '') {
        alert("빠짐없이 작성해주세요.")
    } else {
        if (registUser.userId.length >= 4 && registUser.userId.length <= 16) {
            if (registUser.passwordValid == registUser.password) {
                await userRegist(user.value); 
                alert("회원가입 성공");
                router.push({ name: "user-login" }); 
                return; 
            }
            alert("비번 확인");
            return; 
        }
        alert("아이디 길이 확인");
        return; 
    }  
    console.log("(*) test 1 " + registUser.userId + " 등록 시도");  
};

</script>

<template>

    <v-card class="mx-auto px-6 py-8" style="width: 40vw; margin-top: 100px;" id="regist-form" :elevation="17" >
        <h1 class="custom_h1">회원가입</h1>
        <div style="width: 70%; margin: 0 auto; padding-bottom: 3px;">
            <v-text-field 
                label="아이디"  
                :rules="lenRules"
                v-model="user.userId"
            ></v-text-field>
            <v-text-field 
                label="비밀번호" 
                :type="visible ? 'text' : 'password'" 
                v-model="user.password"
                :rules="lenRules"
            ></v-text-field>
            <v-text-field label="비밀번호 확인" :type="visible ? 'text' : 'password'" :rules="chkRules" v-model="user.passwordValid"></v-text-field>
            <v-text-field label="이름" v-model="user.name" :rules="rules"></v-text-field>
            <v-row>
                <v-col> 
                    <v-text-field label="emailId" v-model="user.emailAccount" :rules="rules"></v-text-field>
                </v-col>
                    <h3 style="line-height: 80px;">@</h3>
                <v-col>
                    <v-text-field label="emailDomain" v-model="user.emailDomain" :rules="rules"></v-text-field>
                </v-col>
            </v-row>
            <v-btn
                block
                class="mb-8"
                color="blue-darken-4"
                size="large"
                variant="tonal"
                @click="regist"
            >
                SignIn
            </v-btn>
        </div>
            
    </v-card>
</template>

<style scoped>
/* #registform{
    width: 50vw;
} */


/* #registform{
    background-color: rgba(103, 141, 158, 0.5);
    width : 40vw;
    height : 85vh; 
    border-radius: 10%;
} */

/* 회원가입 글자 스타일 적용 */
h1{
    font-family: gMedium;
    text-align: center;
}

#regist-form{
    background-color: #f0f6ff;
    width: 60%;
    display: flex;
    flex-direction: column;
    margin: auto;
    font-family: gMedium;
    font-size: 20px;
}

#regist-form label{
    margin-top: 10px;
}

#emailDomain{
    flex : 1;
}

#email{
    width: 100%;
    display: flex;
    flex-direction: row;

}

/* button{
    width : 200px;
    height : 50px;
    font-size: 20px;
    margin : 0 auto;
    margin-top: 30px;
}  */
</style>
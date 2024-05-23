<script setup>
import { ref, onMounted } from 'vue'; 
import { storeToRefs } from "pinia";
import { useMemberStore } from "@/stores/member";
import { useRouter } from "vue-router";

const router = useRouter(); 
const memberStore = useMemberStore();
const { userModify, userDelete } = memberStore;
const { userInfo } = storeToRefs(memberStore);

const user = ref({
    userId: userInfo.value.userId,
    password: "", 
    passwordValid: "", 
    name: userInfo.value.name, 
    emailAccount: userInfo.value.emailAccount, 
    emailDomain: userInfo.value.emailDomain
}); 

onMounted(() => {
    console.log(userInfo.value)
})


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



const modify = async () => {
    const registUser = user.value;
    console.log(registUser);
    // alert("비밀번호 변경!");
    if (registUser.password == '' || registUser.passwordValid == '') {
        alert("빠짐없이 작성해주세요.");
    } else {
        if (registUser.passwordValid == registUser.password) {
            await userModify(user.value); 
            alert("비밀번호 변경 성공");
            router.push({ name: "user-mypage" }); 
            return; 
        }
        alert("비번 확인");
        return; 
    }   
}

const deleteUser = async () => {
    if (confirm("정말로 회원탈퇴 하시겠습니까?") == true) {
        await userDelete(userInfo.value.userId);
        alert("회원탈퇴!");
    } else {
        alert("회원 유지!");
    }
}


</script>

<template>
    <h1 class="custom_h1 title">비밀번호 변경</h1>
    <v-card id="modifyPwd" :variant="'plain'">
        <v-text-field class="vtf"
            label="비밀번호" 
            :type="visible ? 'text' : 'password'" 
            v-model="user.password"
            :rules="lenRules"
        ></v-text-field>
        <v-text-field class="vtf" label="비밀번호 확인" :type="visible ? 'text' : 'password'" :rules="chkRules" v-model="user.passwordValid"></v-text-field>
     <v-btn
            block
            color="blue-darken-4"
            size="large"
            variant="tonal"
            @click="modify"
        >
            비밀번호 변경
        </v-btn>
    </v-card>

    <v-card id="deleteUser"  class="position-relative">
        <v-btn class = "top-n10"
                block
                color="red-lighten-1"
                size="large"
                variant="tonal"
                @click="deleteUser"
            >
            회원 탈퇴
            </v-btn>
    </v-card>
    <v-card id="invisible" class="position-relative">
    
    </v-card>
</template>

<style scoped>

.title{
    display: flex;
    font-family: gMedium;
    font-size: 20px;
}

#modifyPwd{
    display: block;
    margin: 0 auto;
    width: 70%;
}

#deleteUser{
    display: block;
    margin: 0 auto;
    margin-top: 50px;
    margin-bottom: 50px;
    width: 70%;
}

.vtf {
    background-color: #f0f6ff;
}
.position-relative {
  position: relative;
}
</style>
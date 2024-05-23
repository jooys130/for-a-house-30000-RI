<script setup>
import { onMounted } from "vue";
import { storeToRefs } from "pinia";
import { useMemberStore } from "@/stores/member";


const memberStore = useMemberStore();
const { userLike } = memberStore;
const { userInfo, aptCodes, likes } = storeToRefs(memberStore);


onMounted(() => {
    console.log("좋아요 파일입니다.");
    console.log(userInfo)
    console.log(userInfo.value);
    LikeSearchAll(userInfo.value.userId);
})

const LikeSearchAll = async (userId) => {
    await userLike(userId);
}

</script>

<template>
    <v-card id="user-info" class="center-content" :elevation="10"> 
        <v-card-title><b>{{ userInfo.name }}</b>님의 관심 목록</v-card-title>
        <v-divider></v-divider>
        <table>
        <thead>
            <tr>
            <th>아파트 코드</th>
            <th>아파트 이름</th>
            </tr>
        </thead>
        <tbody>
            <tr v-for="(code, index) in aptCodes">
            <td>{{ code }}</td>
            <td>{{ likes[index] }}</td>
            </tr>
        </tbody>
    </table>
    </v-card>
</template>

<style scoped>
.center-content{
    display: block;
    justify-content: center;
    align-items: center;
	width:100%;
	height:auto;
}
table {
    width: 70%;
    border-top: 1px solid #444444;
    border-collapse: collapse;
  }
  th, td {
    border-bottom: 1px solid #444444;
    /* border-left: 1px solid #444444; */
    padding: 10px;
  }
  th:first-child, td:first-child {
    border-left: none;
  }

    th, td {
      padding: 12px;
      border-bottom: 1px solid #ddd;
    }

    /* 테이블 헤더 스타일링 */
    th {
      background-color: #f2f2f2;
    }

    /* 번갈아가며 배경색 적용 */

</style>
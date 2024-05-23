<script setup>
import { ref, onMounted } from 'vue'; 
import { useRouter } from "vue-router";

import { storeToRefs } from "pinia";
import { useMemberStore } from "@/stores/member";


const router = useRouter(); 
const memberStore = useMemberStore();
const { userInfo } = storeToRefs(memberStore);

// onMounted - user 정보 화면에 뿌리기
onMounted(() => {
    console.log(userInfo.value)
})

const user = ref({
    userId: userInfo.value.userId, 
    name: userInfo.value.name, 
    emailAccount: userInfo.value.emailAccount, 
    emailDomain: userInfo.value.emailDomain, 
}); 


const items = ref([
  {
    title: '회원 정보 수정',
    value: 1,
    action: "goModifyUserInfo"
  },
  {
    title: '좋아요',
    value: 2,
    action: 'goUserLike'
  },

])

const menuActionClick = (action) => {
  console.log(action);
  if (action === "goModifyUserInfo") {
    router.push({name : "user-mypage-userInfo"})
  } else if (action === "goUserLike") {
    router.push({name : "user-mypage-mylike"})
  } else {
    alert(" 추후에 기능 추가! ")
  }
}

</script>

<template>
  <div id="mypage_container">
    <div id="item2">
      <v-card id="content2" :elevation="10" >
        <!-- <v-list class="selectItems" color="cyan" :items="items" @click="menuActionClick(items)"></v-list> -->
        <v-list-item-group>
          <v-list-item v-for="(item, index) in items" :key="index" @click="menuActionClick(item.action)">
            <!-- <v-list-item-icon>
              <v-icon dark color="#7E6990" v-text="item.icon"></v-icon>
            </v-list-item-icon> -->
            <v-list-item-title>{{ item.title }}</v-list-item-title>
          </v-list-item>
        </v-list-item-group>
      </v-card>
    </div>
  <div id="item3">
    <v-sheet id="content1" class="d-flex pa-4" :elevation="10">
    <v-card id="user-info" width="400" height="150" :variant="'plain'">
      <v-card-title>{{ user.userId }}</v-card-title>
      <v-divider></v-divider>
      <v-card-text>
        <div class="d-flex flex-column">
          <div class="mb-2">
            <strong>이름:</strong> {{ user.name }}
          </div>
          <div>
            <strong>이메일:</strong> {{ user.emailAccount }}@{{ user.emailDomain }}
          </div>
        </div>
      </v-card-text>
    </v-card>
</v-sheet>
    <v-sheet id="content3" class="mt-4" :elevation="10">
      <router-view></router-view>
    </v-sheet>
  </div>
  </div>
</template>

<style scoped>
#mypage_container{
  width: 60vw;
  height: 100vh;
  margin-top: 30px;
  display: grid;
  grid-template-columns: 1fr 3fr ;
	grid-template-rows: 2fr 5fr ;
  /* row-gap: 10px;s */
}

#item1{
  /* background-color:green; */
  grid-column-end: span 3;
}
#item2{
  /* background-color: blue; */
  padding: 10px;
}
#item3{
  /* background-color: red; */
  padding: 10px;
}

#content1{
  background-color:#f0f6ff;
  width: 100%;
  margin: auto auto;
  display: flex;
  justify-content:space-around;
  text-align: center;
}

#content2{
  /* background-color: blue; */
  width: 100%;
  /* height: 50%; */
  margin: 0 auto;
}

#content3{
  background-color: #ebf3ff;
  width: 100%;
  height: auto;
  margin: 20px auto;
}

.selectItems{
  background-color: teal-lighten-5;
}

#user-info{
  background-color: inherit;
  display: flex;
  flex-direction: column;
}
</style>
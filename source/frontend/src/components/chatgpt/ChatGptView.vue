<script setup>
import { ref } from 'vue'; 
import { useMemberStore } from "@/stores/member";
import { storeToRefs } from "pinia/dist/pinia";

const memberStore = useMemberStore();
const { responseByGPT  } = memberStore;
const { chatGptResponse } =storeToRefs(memberStore);


const question = ref('');
const isChatbotOpen = ref(false);
const toggleChatbot = () => {
  isChatbotOpen.value = !isChatbotOpen.value;
}


const sendQuestion = async () => {
  let question = document.querySelector('#input_btn').value;
  console.log(question);
  let template = `<div class="line">
        <span class="chat-box mine">${question}</span>
      </div>`;
    document.querySelector('.chat-content').insertAdjacentHTML('beforeend', template);
  try {
    await responseByGPT({ question })
    console.log(chatGptResponse)
    let template2 = `<div class="line">
            <span class="chat-box">${chatGptResponse.value}</span>
          </div>`
      document.querySelector('.chat-content').insertAdjacentHTML('beforeend', template2); 
  } catch (error) {
    console.error("Error in sendQuestion:", error);
}
}

</script>
<template>
  <button class="chatbot-toggle-button" @click="toggleChatbot">🔔</button>
  <div v-if="isChatbotOpen" class="chatbot">
    <b-container class="text-center chatbot-wrapper">
      <h2 class="h2-title">집찾아삼만리 챗봇</h2>
      <div class="chat-content">
        <div class="line">
          <span class="chat-box">안녕하세요. <br>
          무엇이든 물어보세요!</span>
        </div>
        <!-- 여기에 채팅 내용을 동적으로 추가하는 로직을 구현할 수 있습니다. -->
      </div>
      <div class="input-container">
        <input v-model="question.value" id="input_btn" placeholder="질문을 입력하세요" />
        <v-btn
          block
          color="blue-darken-4"
          size="large"
          variant="tonal"
          @click="sendQuestion"
        >
        </v-btn>
      </div>

    </b-container>
  </div>
</template>
  
<script>
export default {
  data() {
    return {
      isChatbotOpen: false,
    };
  },
  methods: {
    toggleChatbot() {
      this.isChatbotOpen = !this.isChatbotOpen;
    },
  },
};
</script>

<style>
.h2-title {
  margin-top: 30px;
}

.chat-content {
  height: 800px;
  font-size: 14px;
  overflow-y: scroll;
}
.space{
  height: 50px;
}
.line {
  margin-top: 10px;
  display: flex;
}

.chat-box {
  background: #eee;
  padding: 5px;
  max-width: 50%;
}

.chatbot-wrapper {
  display: flex;
  flex-direction: column;
  height: 100%;
}

.mine {
  margin-left: auto;
}
.searchBtn{
  z-index: 2000;
}

.chatbot-toggle-button {
  position: fixed;
  bottom: 20px;
  right: 20px;
  background-color: #f0f6ff;
  color: white;
  border: none;
  width: 60px; /* 동그란 버튼의 크기 */
  height: 60px; /* 동그란 버튼의 크기 */
  border-radius: 50%;
  cursor: pointer;
  z-index: 1000;
  display: flex;
  align-items: center;
  justify-content: center;
  font-family: 'NanumBarun';
  font-size: 24px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1); /* 약간의 그림자 효과 추가 */
}

.chatbot {
  position: fixed;
  bottom: 80px; /* 버튼 위에 위치 */
  right: 20px;
  width: 300px;
  height: 100%;
  max-height: 500px;
  background: white;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  border-radius: 10px;
  overflow: hidden;
  font-family: 'NanumBarun';
  z-index: 999;
}

.input-container {
  display: flex;
  align-items: center;
  padding-left: 20px;
  width: 100%;
  background-color: #f0f6ff;
}

.input {
  flex: 1;
  padding: 10px;
  border: 1px solid #ddd;
  border-radius: 5px;
  margin-right: 10px;
}

#input_btn{
  height: 100%;
  z-index: 1000;
}

@font-face{
  font-family: 'NanumBarun';
  font-weight: 800;
  src:url("../../assets/fonts/ttf/NanumBarunGothic-YetHangul.ttf")
}
</style>
<script setup>
import VIntroduction from "../components/main/VIntroduction.vue";
import SlidingMenuCard from '../components/main/SlidingMenuCard.vue';
import FooterVue from "../components/main/FooterCard.vue";
import ChatGPTVue from "../components/chatgpt/ChatGptView.vue";
</script>

<template>
    <div class="view_container">
        <VIntroduction/>
        <SlidingMenuCard/>
        <FooterVue/>
        <ChatGPTVue/>
    </div>  
</template>

<style scoped>

.view_container {
    display: flex;
    flex-direction: column;

}
</style>
<script setup>

</script>

<template>
    <div id="container">
        <router-view></router-view>
    </div>
</template>

<style scoped>
#container{
    /* 화면 정중앙 배치 */
    display: flex;
    /* margin-top: 0vh; */
    justify-content: center;
    /* align-items: center; */
    background-color: white;
    height: 100vh;
}
</style>
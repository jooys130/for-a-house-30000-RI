<script setup>
import TheHeadingNavbar from "./components/layout/TheHeadingNavbar.vue";
</script>

<template>
  <div id="outer">
    <TheHeadingNavbar></TheHeadingNavbar>
    <router-view></router-view>
  </div>
</template>

<style scoped>
#outer {
  width: 100%;
  height:100vh;
  background-image:url( "./assets/img/index.jpg" );
  background-size: cover;
}

</style>
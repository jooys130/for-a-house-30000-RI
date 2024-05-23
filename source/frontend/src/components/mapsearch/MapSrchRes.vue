<script setup>
// import { ref, watch } from "vue";
import AptListItem from "@/components/apt/AptListItem.vue";
import { storeToRefs } from "pinia";
import { useMapStore } from "@/stores/map";

const props = defineProps({
  //선택한 지역 정보 받기
  //시도,구군,동 배열
  results: Array,
});

const mapStore = useMapStore();
const { selectApt } = storeToRefs(mapStore);

const viewApt = (currentApt) => {
  selectApt.value = currentApt; 
  // console.log(selectApt.value);
}
</script>

<template>
  <v-sheet 
    class="d-flex flex-column align-center mb-6 bg-surface-variant pa-lg-6" 
    width="20vw"
    height="100vh"
    id="container"
  >
    <template v-if="props.results.length > 0">
      <v-virtual-scroll
        :items="[0]"
      ><AptListItem v-for="result in props.results" :key="result.value" :result="result" @click="viewApt(result)"></AptListItem>
      </v-virtual-scroll>
    </template>
    <template v-else>
      <div>
        조회되는 매매 정보가 없습니다. 
      </div>
    </template>
  </v-sheet>
</template>

<style scoped>
#container {
  /* width: 300px; */
  background-color: rgba(80, 109, 122) !important;
}
</style>

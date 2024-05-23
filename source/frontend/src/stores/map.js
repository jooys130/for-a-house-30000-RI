import { ref } from "vue";
import { defineStore } from "pinia";

export const useMapStore = defineStore("mapStore", () => {
    // 마커 위치(위도, 경도) 저장 배열
    const selectApt = ref({}); 

    return {
        selectApt, 
   };
});

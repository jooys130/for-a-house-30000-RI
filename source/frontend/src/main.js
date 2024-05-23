import './assets/main.css'

import { createApp } from "vue";
import { createPinia } from "pinia";
import piniaPluginPersistedstate from "pinia-plugin-persistedstate";

import App from "./App.vue";
import router from "./router";

// Vuetify
import 'vuetify/styles'
import { createVuetify } from 'vuetify'
import * as components from 'vuetify/components'
import * as directives from 'vuetify/directives'

// HighCharts 
import HighchartsVue from 'highcharts-vue'

const app = createApp(App);
const pinia = createPinia();

const vuetify = createVuetify({
  components,
  directives,
})

pinia.use(piniaPluginPersistedstate);
 
app.use(pinia);
app.use(router);
app.use(vuetify);
app.use(HighchartsVue); 

router.isReady().then(() => {
  app.mount("#app");
});

// app.mount("#app");

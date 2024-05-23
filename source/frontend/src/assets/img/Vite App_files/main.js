import "/src/assets/main.css"

import { createApp } from "/node_modules/.vite/deps/vue.js?v=6d2f8bef";
import { createPinia } from "/node_modules/.vite/deps/pinia.js?v=27f1476d";
import piniaPluginPersistedstate from "/node_modules/.vite/deps/pinia-plugin-persistedstate.js?v=27d8d394";

import App from "/src/App.vue";
import router from "/src/router/index.js";

// Vuetify
import "/node_modules/vuetify/lib/styles/main.css"
import { createVuetify } from "/node_modules/.vite/deps/vuetify.js?v=49b899c4"
import * as components from "/node_modules/.vite/deps/vuetify_components.js?v=c199edd5"
import * as directives from "/node_modules/.vite/deps/vuetify_directives.js?v=a47aa294"

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

router.isReady().then(() => {
  app.mount("#app");
});

// app.mount("#app");

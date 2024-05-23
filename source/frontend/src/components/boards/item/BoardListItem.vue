<script setup>
defineProps({ article: Object });

</script>

<template>
  <tr class="text-center">
    <th scope="row">{{ article.noticeNo }}</th>
    <td class="text-start">
      <router-link
        :to="{ name: 'article-view', params: { noticeNo: article.noticeNo } }"
        class="article-title link-dark"
      >
        {{ article.subject }}
      </router-link>
    </td>
    <td>{{ article.userId }}</td>
    <td>{{ article.hit }}</td>
    <td>{{ article.createdTime }}</td>
  </tr>
</template>

<style scoped>
</style>

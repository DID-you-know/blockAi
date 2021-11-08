import { createRouter, createWebHistory } from "vue-router";
import Home from "@/views/Home";
import ImageTest from "@/views/ImageTest";

const routes = [
  // 메인 페이지
  {
    path: "/",
    name: "Home",
    component: Home,
  },
  {
    path: "/image",
    name: "Image",
    component: ImageTest,
  },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;

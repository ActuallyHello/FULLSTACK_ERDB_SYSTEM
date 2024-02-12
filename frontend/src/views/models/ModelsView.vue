<template>
    <div>
        <section class="hero is-small is-primary has-text-centered mt-3 mb-3">
            <div class="hero-body">
                <p class="subtitle">
                    Бизнес-процесс: Склад
                </p>
            </div>
        </section>

        <section class="mt-3">
            <div class="columns is-multiline">
                <div
                    v-for="model of models"
                    :key="model.id" 
                    class="column is-one-third-desktop is-half-tablet is-full-mobile"
                >
                    <ModelPreview 
                        :modelDetail="{
                            'id': model.id,
                            'title': model.title,
                            'description': model.description,
                            'author': model.author,
                            'position': model.author_info,
                            'date': model.date_created,
                        }"
                        @clickOnDeleteModel="clickOnDeleteModel"
                        @clickOnOpenModel="clickOnOpenModel"
                    />
                </div>
                <!-- <div class="column is-one-third-desktop is-half-tablet is-full-mobile">
                    <ModelPreview 
                        :modelDetail="{
                            'title': 'Складской учёт товаров',
                            // 'description': 'Описание модели хранения товаров на складе. Учёт всех товаров, привязанных к магазину Пятерочка.',
                            'author': 'Смердов Евгений Олегович',
                            'position': 'Магистр',
                            'date': '11:09 PM - 1 Jan 2016',
                        }"
                    />
                </div>
                <div class="column is-one-third-desktop is-half-tablet is-full-mobile">
                    <ModelPreview 
                        :modelDetail="{
                            'title': 'Складской учёт товаров',
                            // 'description': 'Описание модели хранения товаров на складе. Учёт всех товаров, привязанных к магазину Пятерочка.',
                            'author': 'Смердов Евгений Олегович',
                            'position': 'Магистр',
                            'date': '11:09 PM - 1 Jan 2016',
                        }"
                    />
                </div> -->
            </div>
        </section>
    </div>
</template>
  
<script>
import ModelPreview from '@/components/models/ModelPreviewComponent.vue'
import axios from 'axios';
import { toast } from 'bulma-toast'

export default {
    name: 'ModelsView',
    components: {
        ModelPreview
    },
    data() {
        return {
            models: [],
        }
    },
    mounted() {
        this.getModels();
    },
    methods: {
        async getModels() {
            const topic = this.$route.query.topic;
            let getQueryParam;
            if (topic) {
                getQueryParam = `?topic=${topic}`;
            } else {
                getQueryParam = '';
            }

            await axios
                .get(`models/${getQueryParam}`)
                .then(response => {
                    this.models = response.data.models;
                })
                .catch(error => {
                    console.log("ERROR", error);
                    toast({
                        message: "Ошибка при получении модели! Обратитесь к адиминстратору!",
                        type: 'is-danger',
                        dismissible: true,
                        pauseOnHover: true,
                        duration: 2500,
                        position: 'bottom-right',
                    });
                })
        },

        async clickOnOpenModel(id) {
            this.$router.push(`/models/${id}/`);
        },

        async clickOnDeleteModel(id) {
            await axios
                .post(`models/delete-model/`, { "model_id": id })
                .then(response => {
                    this.models = this.models.filter(item => item.id != id);
                    toast({
                        message: `Модель с ID ${id} была удалена!`,
                        type: 'is-info',
                        dismissible: true,
                        pauseOnHover: true,
                        duration: 2500,
                        position: 'bottom-right',
                    });
                })
                .catch(error => {
                    console.log("ERROR", error);
                    toast({
                        message: "Ошибка при удалении модели! Обратитесь к адиминстратору!",
                        type: 'is-danger',
                        dismissible: true,
                        pauseOnHover: true,
                        duration: 2500,
                        position: 'bottom-right',
                    });
                })
        }
    }
}
</script>

<style scoped>
    .card-text {
        height: 70px;
        overflow-y: hidden;
    }
</style>
  
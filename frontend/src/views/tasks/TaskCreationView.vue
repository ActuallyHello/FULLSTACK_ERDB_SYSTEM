<template>
    <div class="box" style="min-height: 99vh; max-height: 100%;">
        <div class="columns is-multiline">
            <div class="column">
                <div class="field">
                    <label class="label">Найти</label>
                    <div class="control">
                        <input type="text" placeholder="Название модели" class="input">
                    </div>
                </div>
            </div>
            <div class="column"></div>
        </div>
        <hr>
        <div class="columns">
            <div class="column">
                <div class="subtitle has-text-centered">Модели</div>
                <hr>
                <div 
                    class="card" 
                    v-for="model of models"
                    :key="model.id"
                >
                    <header class="card-header">
                        <p class="card-header-title">
                            {{ model.title }} # {{ model.id }}
                        </p>
                    </header>
                    <div class="card-content">
                        {{ model.description }} <br>
                        {{ model.topic }} <br>
                        {{ model.date_created }}
                    </div>
                    <div class="columns m-3">
                        <div class="column">
                            <button class="button is-fullwidth is-info">Открыть</button>
                        </div>
                        <div class="column">
                            <label class="checkbox">
                                <input type="checkbox" :value="model.id" v-model="modelsForTaskList" @change="console.log('models',modelsForTaskList)">
                                Выбрать
                            </label>
                            <!-- <button 
                                class="button is-fullwidth is-primary"
                                
                                @click="choseModelForTask(model.id)"
                            >Выбрать</button> -->
                        </div>
                    </div>
                </div>
            </div>
            <div class="column">
                <div class="subtitle has-text-centered">Настройки</div>
                <hr>
                <div class="field">
                    <label class="label">Название задания:</label>
                    <div class="control">
                        <input v-model="taskTitle" class="input" type="text">
                    </div>
                </div>
                <div class="field">
                    <label class="label">Количество тестовых данных:</label>
                    <div class="control">
                        <input v-model="amountOfData" class="input" type="number" >
                    </div>
                </div>
                <div class="field">
                    <label class="label">Описание задания:</label>
                    <div class="control">
                        <textarea v-model="taskDescription" class="textarea"></textarea>
                    </div>
                </div>
                <div class="field">
                    <label class="label">Сложность:</label>
                    <div class="select">
                        <select v-model="complexity">
                            <option value="легко" selected>легко</option>
                            <option value="нормально">нормально</option>
                            <option value="сложно">сложно</option>
                        </select>
                    </div>
                </div>
                <button class="button is-info" @click="createTask">Создать</button>
            </div>
        </div>
    </div>
</template>
  
<script>
import axios from 'axios';
import { toast } from 'bulma-toast'
import { toHandlers } from 'vue';

export default {
    name: 'TaskCreationView',
    data() {
        return {
            models: [],
            modelsForTaskList: [],
            complexity: 'легко',
            taskTitle: '',
            taskDescription: '',
            amountOfData: 0
        }
    },
    mounted() {
        this.getModels();
    },
    methods: {
        async getModels() {
            await axios
                .get(`models/`)
                .then(response => {
                    this.models = response.data.models;
                })
                .catch(error => {
                    console.log("ERROR", error);
                    toast({
                        message: "Ошибка при получении моделей! Обратитесь к адиминстратору!",
                        type: 'is-danger',
                        dismissible: true,
                        pauseOnHover: true,
                        duration: 2500,
                        position: 'bottom-right',
                    });
                })
        },

        choseModelForTask(id) {
            console.log("CHOSEN ", id);

        },

        async createTask() {
            await axios
                .post(`tasks/create-task/`, {
                    title: this.taskTitle,
                    description: this.taskDescription,
                    test_data_amount: this.amountOfData,
                    complexity: this.complexity,
                    model_id_list: this.modelsForTaskList
                })
                .then(response => {
                    toast({
                        message: "Задание создано!",
                        type: 'is-primary',
                        dismissible: true,
                        pauseOnHover: true,
                        duration: 2500,
                        position: 'bottom-right',
                    });
                })
                .catch(error => {
                    console.log("ERROR", error);
                    toast({
                        message: "Ошибка при получении моделей! Обратитесь к адиминстратору!",
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

<style scoped></style>
  
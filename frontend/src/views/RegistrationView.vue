<template>
    <div class="container">
        <div class="columns">
            <div class="column is-4 is-offset-4">
                <h1 class="title">Регистрация</h1>

                <form @submit.prevent="submitForm">
                    <div class="field">
                        <label>Логин</label>
                        <div class="control">
                            <input type="text" class="input" v-model="username" required>
                        </div>
                    </div>

                    <div class="field">
                        <label>Пароль</label>
                        <div class="control">
                            <input type="password" readonly name="password" class="input" v-model="password" required>
                        </div>
                    </div>

                    <div class="field">
                        <label>Имя</label>
                        <div class="control">
                            <input type="text" class="input" v-model="name" required>
                        </div>
                    </div>

                    <div class="field">
                        <label>Фамилия</label>
                        <div class="control">
                            <input type="text" name="surname" class="input" v-model="surname" required>
                        </div>
                    </div>

                    <div class="field">
                        <label>Группа</label>
                        <div class="control">
                            <div class="select">
                                <select v-model="idGroup">
                                    <option value="2">ИВТ-21</option>
                                    <option value="3">ИСТ-1-21</option>
                                    <option value="4">ИСТ-2-21</option>
                                </select>
                            </div>
                        </div>
                    </div>

                    <div class="notification is-danger" v-if="errors.length">
                        <p v-for="error in errors" v-bind:key="error">{{ error }}</p>
                    </div>

                    <div class="field">
                        <div class="control">
                            <button class="button is-success">Регистрация</button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</template>

<script>
import axios from 'axios'
import {toast} from 'bulma-toast'

export default {
    name: 'Registration',
    data() {
            return {
                username: '',
                password: 'test12345678',
                name: '',
                surname: '',
                idGroup: '',
                errors: []
            }
        },
    mounted() {
        localStorage.clear();
        
        this.$store.commit('setUser', null);
        this.$store.commit('setAuthenticated', false);
    },
    methods: {
        submitForm() {
            const formData = {
                username: this.username,
                password: this.password,
                first_name: this.name,
                last_name: this.surname,
                group_id: this.idGroup,
            };
            axios
                .post(`http://${this.$store.state.url}:8000/persons/register-student/`, formData)
                .then(response => {
                    console.log(response);
                    toast({
                        message: 'Регистрация прошла успешна!',
                        type: 'is-success',
                        dismissible: true,
                        pauseOnHover: true,
                        duration: 5000,
                        position: 'bottom-right',
                    })
                    this.$router.push('/log-in')
                })
                .catch(error => {
                    console.log(error)
                    toast({
                        message: `Ошибка! Возможно логин уже занят!`,
                        type: 'is-danger',
                        dismissible: true,
                        pauseOnHover: true,
                        duration: 2000,
                        position: 'bottom-right',
                    })
                });
            
            this.username = '';
            this.name = '';
            this.surname = '';
        }
    }
}
</script>
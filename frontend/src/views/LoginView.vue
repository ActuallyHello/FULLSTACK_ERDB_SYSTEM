<template>
    <div class="container">
        <div class="columns">
            <div class="column is-4 is-offset-4">
                <h1 class="title">Авторизация</h1>

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
                            <input type="password" name="password" class="input" v-model="password" required>
                        </div>
                    </div>

                    <div class="notification is-danger" v-if="errors.length">
                        <p v-for="error in errors" v-bind:key="error">{{ error }}</p>
                    </div>

                    <div class="field">
                        <div class="control">
                            <button class="button is-success">Войти</button>
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
    name: 'Login',
    data() {
            return {
                username: '',
                password: '',
                errors: []
            }
        },
    mounted() {
        localStorage.clear();
    },
    methods: {
        setLocalStorageUserCredentials(user) {
            localStorage.setItem('user_id', user?.user_id);
            localStorage.setItem('person_id', user?.person_id);
            localStorage.setItem('type', user?.type);
            localStorage.setItem('first_name', user?.first_name);
            localStorage.setItem('last_name', user?.last_name);
            localStorage.setItem('middle_name', user?.middle_name);
            localStorage.setItem('teacher_id', user?.teacher_id);
            localStorage.setItem('student_id', user?.student_id);
            localStorage.setItem('group', user?.group);
            localStorage.setItem('position', user?.position);
        },
        submitForm() {
            const formData = {
                username: this.username,
                password: this.password,
            };
            axios
                .post(`persons/auth-user/`, formData)
                .then(response => {
                    console.log("WE AXIOS", formData)
                    console.log(response);
                    this.setLocalStorageUserCredentials(response.data.user)
                    localStorage.setItem('isAuth', true);
                    // this.$store.commit('setUser', response.data.user);
                    // this.$store.commit('setAuthenticated', true);
                    toast({
                        message: 'Авторизация прошла успешна!',
                        type: 'is-success',
                        dismissible: true,
                        pauseOnHover: true,
                        duration: 5000,
                        position: 'bottom-right',
                    })
                    this.$router.push('/')
                })
                .catch(error => {
                    console.log("ERROR")
                    console.log(error)
                    toast({
                        message: 'Неверный логин или пароль!',
                        type: 'is-danger',
                        dismissible: true,
                        pauseOnHover: true,
                        duration: 2000,
                        position: 'bottom-right',
                    })
                });
            
            this.username = '';
            this.password = '';
        }
    }
}
</script>
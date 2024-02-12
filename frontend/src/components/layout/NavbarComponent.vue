<template>
    <div>
        <nav class="navbar is-dark">
            <div class="navbar-brand">
                <router-link to="/" class="navbar-item">
                    <strong>ERDB SYSTEM</strong>
                </router-link>
            </div>

            <div class="navbar-menu">
                <div class="navbar-start">
                    <router-link to="/" class="navbar-item">Модели</router-link>
                </div>
                <div class="navbar-end">
                    <div class="navbar-item">
                        <div class="buttons">
                            <router-link to="/" class="button is-primary">Войти</router-link>
                        </div>
                    </div>
                </div>
            </div>
        </nav>
    </div>
</template>

<script>
export default {
    name: 'Navbar',
}
</script>
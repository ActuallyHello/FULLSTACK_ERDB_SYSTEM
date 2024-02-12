<template>
    <Navbar />
    <div id="main" class="columns is-mobile is-centered">
        <div class="column is-four-fifths">
            <router-view />
        </div>
    </div>
</template>

<script>
import NavbarComponent from '@/components/layout/NavbarComponent.vue';

export default {
    name: 'App',
    components: {
        Navbar: NavbarComponent
    }
}
</script>

<style lang="scss">
div#main {
    background-color: aliceblue;
    min-height: 99vh;
    max-height: 100%;
}

@import '../node_modules/bulma'
</style>

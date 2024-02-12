<template>
    <div>
        <div class="card">
            <header class="card-header">
                <p class="card-header-title has-text-centered">
                    {{ modelDetail.title }} # {{ modelDetail.id }}
                </p>
            </header>
            <div class="card-content">
                <div class="content">
                    <div class="card-text">
                        {{ modelDetail.description }}
                    </div>
                    <hr class="my-2">
                    <p> <strong>{{ modelDetail.author }} <br> {{ modelDetail.position }}</strong></p>
                    <time datetime="2016-1-1">{{ modelDetail.date }}</time>
                </div>
            </div>
            <footer class="card-footer">
                <a 
                    href="#" 
                    class="card-footer-item"
                    @click="clickOnOpenModel"
                >Открыть</a>
                <a href="#" class="card-footer-item">Изменить</a>
                <a 
                    href="#" 
                    class="card-footer-item"
                    @click="clickOnDeleteModel"
                >Удалить</a>
            </footer>
        </div>
    </div>
</template>

<script>
export default {
    name: 'ModelPreview',
    props: ['modelDetail'],
    methods: {
        clickOnDeleteModel() {
            this.$emit('clickOnDeleteModel', this.modelDetail.id);
        },
        clickOnOpenModel() {
            this.$emit('clickOnOpenModel', this.modelDetail.id);
        }
    }
}
</script>
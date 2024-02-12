<template>
    <div class="box" style="min-height: 99vh; max-height: 100%;">
        <div class="columns is-multiline is-variable">
            <div class="column is-6-desktop is-12-mobile">
                <section class="hero is-small is-primary has-text-centered mb-3">
                    <div class="hero-body">
                        <p class="subtitle">
                            Задания на выполнение
                        </p>
                    </div>
                </section>
                <div class="card">
                    <header class="card-header">
                        <p class="card-header-title">
                            Задание 1
                        </p>
                    </header>
                    <div class="card-content">
                        <div class="content">
                            <label class="label">Описание:</label>
                            Lorem ipsum dolor sit amet, consectetur adipiscing elit. Phasellus nec iaculis mauris. <br>
                            <time datetime="2016-1-1">11:09 PM - 1 Jan 2016</time>
                        </div>
                    </div>
                    <footer class="card-footer">
                        <a href="#" class="card-footer-item">Выполнить</a>
                    </footer>
                </div>

            </div>
            <div class="column is-half-tablet is-12-mobile">
                <section class="hero is-small is-primary has-text-centered mb-3">
                    <div class="hero-body">
                        <p class="subtitle">
                            Выполненные задания
                        </p>
                    </div>
                </section>
                <div class="card">
                    <header class="card-header">
                        <p class="card-header-title">
                            Результат № <br>
                            Задание:  № <br>
                            Сложность:  <br>
                            Отправлено:
                        </p>
                    </header>
                    <div class="card-content">
                        Оценка: <strong>  </strong> <br>
                        Проверил: <strong>  </strong>
                        <br>
                        Проверенно: <strong> </strong>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>
  
<script>

export default {
    name: 'MyTasksView',

}
</script>

<style scoped></style>
  
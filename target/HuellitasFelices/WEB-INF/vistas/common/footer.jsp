</main>
<footer class="bg-dark text-light text-center py-3 mt-auto">
    <div class="container">
        <small>
            &copy; 2026 Centro de Adopción <strong>Huellitas Felices</strong> &mdash;
            Proyecto de Cátedra POO404 &mdash; Universidad Don Bosco
        </small>
    </div>
</footer>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
<script>
    // Confirmación de eliminación
    function confirmarEliminar(url, nombre) {
        if (confirm('¿Está seguro que desea eliminar "' + nombre + '"?\nEsta acción no se puede deshacer.')) {
            window.location.href = url;
        }
    }
</script>
</body>
</html>

// Example JavaScript to load products dynamically (placeholder)
document.addEventListener('DOMContentLoaded', function() {
    const productList = document.getElementById('product-list');

    // Mock product data
    const products = [
        { id: 1, name: "Product 1", price: "$10" },
        { id: 2, name: "Product 2", price: "$20" },
        { id: 3, name: "Product 3", price: "$30" },
    ];

    // Load products into the DOM
    products.forEach(product => {
        const div = document.createElement('div');
        div.className = 'col-md-4';
        div.innerHTML = `
            <div class="card mb-4">
                <div class="card-body">
                    <h5 class="card-title">${product.name}</h5>
                    <p class="card-text">${product.price}</p>
                    <a href="#" class="btn btn-primary">Add to Cart</a>
                </div>
            </div>
        `;
        productList.appendChild(div);
    });
});

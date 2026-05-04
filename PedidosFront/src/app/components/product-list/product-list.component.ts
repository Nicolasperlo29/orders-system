import { Component } from '@angular/core';
import { Product } from '../../interfaces/product';
import { ProductService } from '../../services/product.service';
import { Cart } from '../../interfaces/cart';
import { OrderItem } from '../../interfaces/order-item';
import { Order } from '../../interfaces/order';
import { FormsModule } from '@angular/forms';
import { RouterLink } from "@angular/router";
import { OrderService } from '../../services/order.service';
import { DecimalPipe } from '@angular/common';

@Component({
  selector: 'app-product-list',
  standalone: true,
  imports: [FormsModule, RouterLink, DecimalPipe],
  templateUrl: './product-list.component.html',
  styleUrl: './product-list.component.scss'
})
export class ProductListComponent {

  order: Order = {};
  orderItem: OrderItem[] = [];
  products: Product[] = [];
  cantidad: number = 1;
  cantidades: { [key: number]: number } = {};

  constructor(private productService: ProductService, private orderService: OrderService) { }

  ngOnInit() {
    this.productService.getProducts().subscribe(data => {
      this.products = data;
    });

    const orderGuardada = this.orderService.getOrder();

    if (orderGuardada && orderGuardada.items) {
      this.order = orderGuardada;
      this.orderItem = orderGuardada.items;
    }

    this.productService.getProducts().subscribe(data => {
      this.products = data;
      console.log('Productos obtenidos:', this.products);
    });

    console.log('OrderItem inicial:', this.orderItem);
  }

  persistirCarrito() {
    this.order.items = this.orderItem;
    this.orderService.setOrder(this.order);
  }

  agregar(product: Product) {
    const cantidad = this.cantidades[product.id!] || 1;

    const existente = this.orderItem.find(i => i.productId === product.id);

    if (existente) {
      existente.quantity += cantidad;
    } else {
      this.orderItem.push({
        productId: product.id,
        quantity: cantidad,
        price: product.price
      });
    }

    this.persistirCarrito();
  }

  confirmar() {
    this.order.userId = 1; // Simulamos un usuario con ID 1
    this.order.isDelivery = true; // Simulamos que es para entrega
    this.order.total = this.orderItem.reduce((total, item) => {
      const product = this.products.find(p => p.id === item.productId);
      return total + (product ? product.price * item.quantity : 0);
    }, 0);
    this.order.orderNumber = Math.floor(Math.random() * 1000000); // Generamos un número de orden aleatorio
    this.order.items = this.orderItem;
    console.log('Orden confirmada: ', this.order);
    this.orderService.orderActual = this.order; // Guardamos la orden en el servicio
  }

  eliminarItem(index: number) {
    this.orderItem.splice(index, 1);
    this.persistirCarrito();
  }

  getProductName(id: number | undefined): string {
    return this.products.find(p => p.id === id)?.name ?? `#${id}`;
  }

  getSubtotal(item: OrderItem): number {
    const product = this.products.find(p => p.id === item.productId);
    return product ? product.price * (item.quantity ?? 1) : 0;
  }

  getTotal(): number {
    return this.orderItem.reduce((acc, item) => acc + this.getSubtotal(item), 0);
  }
}

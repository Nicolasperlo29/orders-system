import { Component, OnInit } from '@angular/core';
import { OrderService } from '../../services/order.service';
import { CommonModule } from '@angular/common';
import { FormsModule, NgModel } from '@angular/forms';
import { RouterLink } from "@angular/router";

@Component({
  selector: 'app-order',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './order.component.html',
  styleUrl: './order.component.css'
})
export class OrderComponent implements OnInit {

  order: any;
  constructor(private orderService: OrderService) { }

  ngOnInit() {
    this.order = this.orderService.orderActual;
    console.log('Orden actual en OrderComponent: ', this.order);
  }

  confirmar() {
    this.orderService.confirmarOrden(this.order).subscribe({
      next: (res) => {
        console.log('Orden enviada OK', res);
      },
      error: (err) => {
        console.error('Error al enviar orden', err);
      }
    });
  }

  guardarCambios() {
    this.recalcularTotal();
    this.orderService.setOrder(this.order);
  }

  eliminarItem(index: number) {
    this.order.items.splice(index, 1);
    this.recalcularTotal();
    this.orderService.setOrder(this.order);
  }

  recalcularTotal() {
    this.order.total = this.order.items.reduce((total: number, item: any) => {
      return total + (item.price * item.quantity);
    }, 0);
  }
}

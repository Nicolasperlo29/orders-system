import { Component, OnInit, signal, computed } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink } from "@angular/router";
import { User } from '../../interfaces/user';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {

  users = signal<User[]>([]);
  loading = signal(true);
  error = signal('');
  sidebarOpen = signal(false);

  constructor(private authService: AuthService, private router: Router) { }

  ngOnInit() {
    this.authService.getUsers().subscribe({
      next: (data) => { this.users.set(data); this.loading.set(false); },
      error: () => { this.error.set('No se pudieron cargar los usuarios'); this.loading.set(false); }
    });
  }

  logout() {
    this.authService.logout();
    this.router.navigate(['/login']);
  }

  getInitials(name: string): string {
    return name.split(' ').map(n => n[0]).slice(0, 2).join('').toUpperCase();
  }

  getUserTypeBadge(type: string): string {
    return type === 'ADMIN' ? 'admin' : 'client';
  }

  toggleSidebar() {
    this.sidebarOpen.update(v => !v);
  }
}
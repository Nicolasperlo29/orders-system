import { Component, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { AuthService } from '../../services/auth.service'; 

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {
  name = '';
  email = '';
  password = '';
  showPassword = signal(false);
  loading = signal(false);
  error = signal('');
  success = signal(false);

  constructor(private authService: AuthService, private router: Router) { }

  onSubmit() {
    if (!this.name || !this.email || !this.password) {
      this.error.set('Completá todos los campos');
      return;
    }
    if (this.password.length < 6) {
      this.error.set('La contraseña debe tener al menos 6 caracteres');
      return;
    }
    this.loading.set(true);
    this.error.set('');
    this.authService.register({ name: this.name, email: this.email, password: this.password }).subscribe({
      next: () => {
        this.loading.set(false);
        this.success.set(true);
        setTimeout(() => this.router.navigate(['/login']), 1800);
      },
      error: (err) => {
        this.loading.set(false);
        this.error.set(err.error?.message || 'Error al registrarse. El email puede estar en uso.');
      }
    });
  }

  togglePassword() { this.showPassword.update(v => !v); }

  getPasswordStrength(): { level: number; label: string } {
    const p = this.password;
    if (!p) return { level: 0, label: '' };
    let score = 0;
    if (p.length >= 8) score++;
    if (/[A-Z]/.test(p)) score++;
    if (/[0-9]/.test(p)) score++;
    if (/[^A-Za-z0-9]/.test(p)) score++;
    const labels = ['', 'Débil', 'Regular', 'Buena', 'Fuerte'];
    return { level: score, label: labels[score] || 'Fuerte' };
  }
}
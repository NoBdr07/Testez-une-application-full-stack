import { HttpClientModule } from '@angular/common/http';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ReactiveFormsModule } from '@angular/forms';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { RouterTestingModule } from '@angular/router/testing';
import { expect } from '@jest/globals';
import { SessionService } from 'src/app/services/session.service';
import { LoginComponent } from './login.component';
import { throwError } from 'rxjs';
import { AuthService } from '../../services/auth.service';

describe('LoginComponent', () => {
  let component: LoginComponent;
  let fixture: ComponentFixture<LoginComponent>;
  let authServiceMock: any;

  beforeEach(async () => {

    authServiceMock = {
      login: jest.fn()
    }

    await TestBed.configureTestingModule({
      declarations: [LoginComponent],
      providers: [SessionService, {provide: AuthService, useValue: authServiceMock}],
      imports: [
        RouterTestingModule,
        BrowserAnimationsModule,
        HttpClientModule,
        MatCardModule,
        MatIconModule,
        MatFormFieldModule,
        MatInputModule,
        ReactiveFormsModule]
    })
      .compileComponents();
    fixture = TestBed.createComponent(LoginComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should set the component on error if wrong id/password submit', () => {
    const email = component.form.get('email');
    const password = component.form.get('password');

    authServiceMock.login.mockReturnValue(throwError(() => new Error('Invalid credentials')));

    if (email && password) {
      email.setValue('wrongemail@gmail.com');
      password.setValue('123');
      component.submit();

      fixture.detectChanges();

      expect(component.onError).toBe(true);
    } else {
      fail('email or password not found');
    }
  });

  it('should display an error if one of the fields is empty', () => {
    const email = component.form.get('email');
    const password = component.form.get('password');

    if (email && password) {
      email.setValue('example@MatLine.com');
      password.setValue('');
      
      expect(component.form.valid).toBe(false);
    }
  });



});

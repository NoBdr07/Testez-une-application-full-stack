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
import { RegisterComponent } from './register.component';
import { SessionInformation } from 'src/app/interfaces/sessionInformation.interface';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { of } from 'rxjs';

describe('LoginComponent integration test suites', () => {
  let component: RegisterComponent;
  let fixture: ComponentFixture<RegisterComponent>;
  let authService: AuthService;
  let mockRouter: Router;

  const authServiceMock = {
    register: jest.fn().mockReturnValue(of(void 0))
  }

  mockRouter = {
    navigate: jest.fn(),
  } as unknown as jest.Mocked<Router>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [RegisterComponent],
      providers: [
        { provide: AuthService, useValue: authServiceMock },
        { provide: Router, useValue: mockRouter },
      ],
      imports: [
        RouterTestingModule,
        BrowserAnimationsModule,
        HttpClientModule,
        MatCardModule,
        MatIconModule,
        MatFormFieldModule,
        MatInputModule,
        ReactiveFormsModule,
      ],
    }).compileComponents();

    fixture = TestBed.createComponent(RegisterComponent);
    component = fixture.componentInstance;
    authService = TestBed.inject(AuthService);
    mockRouter = TestBed.inject(Router);
    fixture.detectChanges();
  });

  it('should register and redirect to login page', async () => {

    const authServiceSpy = jest.spyOn(authService, 'register');
    
    const routerSpy = jest.spyOn(mockRouter, 'navigate');
  
    component.form.setValue({ 
        firstName: 'test',
        lastName: 'test',
        email: 'test@mail.com', 
        password: '1234' });

    component.submit();

    await fixture.whenStable();

    expect(authServiceSpy).toHaveBeenCalledWith(component.form.value);
    expect(routerSpy).toHaveBeenCalledWith(['/login']);
  });

});

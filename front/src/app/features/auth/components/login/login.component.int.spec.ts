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
import { SessionInformation } from 'src/app/interfaces/sessionInformation.interface';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { of } from 'rxjs';

describe('LoginComponent integration test suites', () => {
  let component: LoginComponent;
  let fixture: ComponentFixture<LoginComponent>;
  let authService: AuthService;
  let sessionService: SessionService;
  let mockRouter: Router;

  const mockSessionInformation: SessionInformation = {
    token: '',
    type: '',
    id: 1,
    username: '',
    firstName: '',
    lastName: '',
    admin: true,
  };

  mockRouter = {
    navigate: jest.fn(),
  } as unknown as jest.Mocked<Router>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [LoginComponent],
      providers: [
        SessionService,
        AuthService,
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

    fixture = TestBed.createComponent(LoginComponent);
    component = fixture.componentInstance;
    authService = TestBed.inject(AuthService);
    sessionService = TestBed.inject(SessionService);
    mockRouter = TestBed.inject(Router);
    fixture.detectChanges();
  });

  it('should login and redirect to sessions page', () => {
    const authServiceSpy = jest.spyOn(authService, 'login').mockReturnValue(of(mockSessionInformation));
  
    const sessionServiceSpy = jest.spyOn(sessionService, 'logIn');
    
    const routerSpy = jest.spyOn(mockRouter, 'navigate');
  
    component.form.setValue({ email: 'test@mail.com', password: '1234' });
    component.submit();

    expect(authServiceSpy).toHaveBeenCalledWith(component.form.value);
    expect(sessionServiceSpy).toHaveBeenCalled;
    expect(sessionService.isLogged).toBeTruthy;
    expect(sessionService.sessionInformation).toBe(mockSessionInformation);
    expect(routerSpy).toHaveBeenCalledWith(['/sessions']);
  });

});

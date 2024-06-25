import { ComponentFixture, TestBed } from '@angular/core/testing';
import { FormComponent } from './form.component';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { SessionApiService } from '../../services/session-api.service';
import { of } from 'rxjs';
import { expect } from '@jest/globals';
import { MatIconModule } from '@angular/material/icon';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatCardModule } from '@angular/material/card';
import { MatInputModule } from '@angular/material/input';
import { SessionService } from 'src/app/services/session.service';
import { MatSelectModule } from '@angular/material/select';
import { BrowserAnimationsModule, NoopAnimationsModule } from '@angular/platform-browser/animations';
import { Router } from '@angular/router';

describe('FormComponent', () => {
  let component: FormComponent;
  let fixture: ComponentFixture<FormComponent>;
  let sessionApiService: SessionApiService;
  let router: Router;
  let sessionService: SessionService;

  let mockSessionService = {
    sessionInformation: {
      admin: true,
    },
  };

  const mockSessionInformation = {
    name: 'Test Session',
    date: '2023-01-01',
    teacher_id: '1',
    description: 'Test Description',
  };

  const mockSessionApiService = {
    create: jest.fn().mockReturnValue(of(mockSessionInformation))
  };

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [FormComponent],
      imports: [
        ReactiveFormsModule,
        FormsModule,
        HttpClientTestingModule,
        RouterTestingModule.withRoutes([]),
        MatSnackBarModule,
        MatCardModule,
        MatIconModule,
        MatFormFieldModule,
        MatSelectModule,
        MatInputModule,
        ReactiveFormsModule,
        BrowserAnimationsModule,
        NoopAnimationsModule // disable animations in test environment
      ],
      providers: [
        { provide: SessionApiService, useValue: mockSessionApiService },
        { provide: SessionService, useValue: mockSessionService }
      ],
    }).compileComponents();

    fixture = TestBed.createComponent(FormComponent);
    component = fixture.componentInstance;
    sessionApiService = TestBed.inject(SessionApiService);
    sessionService = TestBed.inject(SessionService);
    router = TestBed.inject(Router);
    fixture.detectChanges();
  });

  it('should create a new session', async () => {
    const sessionApiServiceSpy = jest.spyOn(sessionApiService, 'create');

    const routerSpy = jest.spyOn(router, 'navigate');

    component.sessionForm!.setValue(mockSessionInformation);

    component.submit();

    await fixture.whenStable();

    expect(sessionApiServiceSpy).toHaveBeenCalledWith(mockSessionInformation);
    expect(routerSpy).toHaveBeenCalledWith(['sessions']);
  });
});

import { HttpClientModule } from '@angular/common/http';
import {
  ComponentFixture,
  TestBed,
} from '@angular/core/testing';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { SessionService } from 'src/app/services/session.service';
import { expect } from '@jest/globals';
import { MeComponent } from './me.component';
import { formatDate } from '@angular/common';
import { registerLocaleData } from '@angular/common';
import localeEn from '@angular/common/locales/en';

describe('MeComponent Unit test suite', () => {
  let component: MeComponent;
  let fixture: ComponentFixture<MeComponent>;

  const mockSessionService = {
    sessionInformation: {
      admin: true,
      id: 1,
    },
  };
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [MeComponent],
      imports: [
        MatSnackBarModule,
        HttpClientModule,
        MatCardModule,
        MatFormFieldModule,
        MatIconModule,
        MatInputModule,
      ],
      providers: [{ provide: SessionService, useValue: mockSessionService }],
    }).compileComponents();

    fixture = TestBed.createComponent(MeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should go back', () => {
    const spy = jest.spyOn(window.history, 'back');
    component.back();
    expect(spy).toHaveBeenCalled();
  });

  it('should display user information', () => {
    const user = {
      id: 1,
      email: 'test@mail.com',
      lastName: 'test',
      firstName: 'test',
      admin: true,
      password: 'test',
      createdAt: new Date(),
      updatedAt: new Date(),
    };

    component.user = user;
    fixture.detectChanges();
    const compiled = fixture.nativeElement;

    // Format dates to longDate
    registerLocaleData(localeEn);
    const locale = 'en-US';
    const createdAtFormatted = formatDate(user.createdAt, 'longDate', locale);
    const updatedAtFormatted = formatDate(user.updatedAt, 'longDate', locale);

    expect(compiled.textContent).toContain(user.lastName);
    expect(compiled.textContent).toContain(user.firstName);
    expect(compiled.textContent).toContain(user.email);
    expect(compiled.textContent).toContain(createdAtFormatted);
    expect(compiled.textContent).toContain(updatedAtFormatted);
  });
});

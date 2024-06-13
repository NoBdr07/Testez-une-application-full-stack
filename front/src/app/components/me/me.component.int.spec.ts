import { HttpClientModule } from '@angular/common/http';
import {
  ComponentFixture,
  TestBed,
  fakeAsync,
  tick,
} from '@angular/core/testing';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { SessionService } from 'src/app/services/session.service';
import { expect } from '@jest/globals';
import { of } from 'rxjs';
import { spyOn } from 'jest-mock';
import { MeComponent } from './me.component';
import { UserService } from 'src/app/services/user.service';
import { Router } from '@angular/router';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { RouterTestingModule } from '@angular/router/testing';
import {
  HttpClientTestingModule,
  HttpTestingController,
} from '@angular/common/http/testing';

describe('MeComponent Integration test suite', () => {
  let component: MeComponent;
  let fixture: ComponentFixture<MeComponent>;
  let userService: UserService;
  let router: Router;
  let sessionService: SessionService;
  let httpTestingController: HttpTestingController;

  const mockSessionService = {
    sessionInformation: {
      admin: true,
      id: 1,
    },
    logOut: jest.fn(),
  };

  const user = {
    id: 1,
    name: 'test',
    email: 'test@mail.com',
    lastName: 'test',
    firstName: 'test',
    admin: true,
    password: 'test',
    createdAt: new Date(),
    updatedAt: new Date(),
  };

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [MeComponent],
      imports: [
        MatSnackBarModule,
        RouterTestingModule.withRoutes([]),
        HttpClientTestingModule,
        HttpClientModule,
        MatCardModule,
        MatFormFieldModule,
        MatIconModule,
        MatInputModule,
        MatSnackBarModule,
        NoopAnimationsModule,
      ],
      providers: [{ provide: SessionService, useValue: mockSessionService }],
    }).compileComponents();

    fixture = TestBed.createComponent(MeComponent);
    component = fixture.componentInstance;
    userService = TestBed.inject(UserService);
    router = TestBed.inject(Router);
    sessionService = TestBed.inject(SessionService);
    httpTestingController = TestBed.inject(HttpTestingController);
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should get user information', fakeAsync(() => {
    const spy = spyOn(userService, 'getById').mockReturnValue(of(user));
    component.ngOnInit();
    tick();
    expect(component.user).toEqual(user);
  }));

  it('should delete user', async () => {
    const userServiceSpy = spyOn(userService, 'delete');
    const sessionServiceSpy = spyOn(sessionService, 'logOut');
    const routerSpy = spyOn(router, 'navigate');

    component.delete();

    await fixture.whenStable();

    const reqs = httpTestingController.match(`api/user/1`);
    expect(reqs.length).toBe(2);
    reqs[0].flush(user);
    reqs[1].flush('user deleted');

    expect(userServiceSpy).toHaveBeenCalledWith(
      mockSessionService.sessionInformation.id.toString()
    );
    expect(sessionServiceSpy).toHaveBeenCalled();
    expect(routerSpy).toHaveBeenCalledWith(['/']);
    expect(sessionService.isLogged).toBeFalsy();
  });
});

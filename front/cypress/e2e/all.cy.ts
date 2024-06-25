describe('Testing register component', () => {
  it('Register successfull', () => {
    cy.visit('/register');

    cy.intercept('POST', '/api/auth/register', {
      body: {
        email: 'test@mail.com',
        firstName: 'firstName',
        lastName: 'lastName',
        password: 'password',
      },
    });

    cy.intercept(
      {
        method: 'GET',
        url: '/api/session',
      },
      []
    ).as('session');

    cy.get('input[formControlName=email]').type('test@mail.com');
    cy.get('input[formControlName=firstName]').type('firstName');
    cy.get('input[formControlName=lastName]').type('lastName');
    cy.get('input[formControlName=password]').type(
      `${'password'}{enter}{enter}`
    );

    cy.url().should('include', '/login');
  });
});

describe('Testing login component', () => {
  it('Login successfull', () => {
    cy.visit('/login');

    cy.intercept('POST', '/api/auth/login', {
      body: {
        id: 1,
        username: 'userName',
        firstName: 'firstName',
        lastName: 'lastName',
        admin: true,
      },
    });

    cy.intercept(
      {
        method: 'GET',
        url: '/api/session',
      },
      []
    ).as('session');

    cy.get('input[formControlName=email]').type('test@mail.com');
    cy.get('input[formControlName=password]').type(
      `${'test!1234'}{enter}{enter}`
    );

    cy.url().should('include', '/sessions');
  });
});

describe('Testing session component as user', () => {
  beforeEach(() => {
    cy.visit('/login');

    cy.intercept('POST', '/api/auth/login', {
      body: {
        id: 6,
        username: 'userName',
        firstName: 'firstName',
        lastName: 'lastName',
        admin: false,
      },
    });

    cy.intercept(
      {
        method: 'GET',
        url: '/api/session',
      },
      [
        {
          id: 1,
          name: 'Session 1',
          description: 'Description 1',
          date: '2024-06-10',
          teacher_id: 1,
          users: [1, 2],
          createdAt: '2024-06-10',
          updatedAt: '2024-06-10',
        },
        {
          id: 2,
          name: 'Session 2',
          description: 'Description 2',
          date: '2024-06-10',
          teacher_id: 2,
          users: [2, 3],
          createdAt: '2024-06-10',
          updatedAt: '2024-06-10',
        },
      ]
    );

    cy.get('input[formControlName=email]').type('test@mail.com');
    cy.get('input[formControlName=password]').type(
      `${'test123'}{enter}{enter}`
    );
  });

  it('See all sessions as a user', () => {
    cy.get('mat-card-title').should('contain.text', 'Sessions');
    cy.get('mat-card-title').should('contain.text', 'Session 1');
    cy.get('mat-card-title').should('contain.text', 'Session 2');
  });

  it('should see a session details', () => {
    cy.intercept('GET', '/api/session/1', {
      id: 1,
      name: 'Session 1',
      description: 'Description 1',
      date: '2024-06-12',
      teacher_id: 1,
      users: [1, 2, 3, 4],
      createdAt: '2024-06-11',
      updatedAt: '2024-06-10',
    });

    cy.intercept('GET', '/api/teacher/1', {
      id: 1,
      lastName: 'teacherLastName',
      firstName: 'teacherFirstName',
      createdAt: '2024-06-10',
      updatedAt: '2024-06-10',
    });

    cy.get('button').contains('Detail').click();

    cy.get('h1').should('contain.text', 'Session 1');
    cy.get('.description').should('contain.text', 'Description 1');
    cy.get('.ml1').should('contain.text', '4 attendees');
    cy.get('.ml1').should('contain.text', 'June 12, 2024');
    cy.get('.created').should('contain.text', 'June 11, 2024');
    cy.get('.updated').should('contain.text', 'June 10, 2024');
    cy.get('.ml1').should('contain.text', 'teacherFirstName TEACHERLASTNAME');
    cy.get('button').contains('Participate').should('exist');
  });

  it('should show unparticipate button and send a delete request', () => {
    cy.intercept('GET', '/api/session/1', {
      id: 1,
      name: 'Session 1',
      description: 'Description 1',
      date: '2024-06-12',
      teacher_id: 1,
      users: [1, 2, 3, 4, 6],
      createdAt: '2024-06-11',
      updatedAt: '2024-06-10',
    });

    cy.intercept('GET', '/api/teacher/1', {
      id: 1,
      lastName: 'teacherLastName',
      firstName: 'teacherFirstName',
      createdAt: '2024-06-10',
      updatedAt: '2024-06-10',
    });

    cy.intercept('DELETE', '/api/session/1/participate/6', {
      id: 1,
      name: 'Session 1',
      description: 'Description 1',
      date: '2024-06-12',
      teacher_id: 1,
      users: [1, 2, 3, 4],
      createdAt: '2024-06-11',
      updatedAt: '2024-06-10',
    }).as('delete');

    cy.get('button').contains('Detail').click();

    cy.get('button').contains('Do not participate').should('exist');

    cy.get('button').contains('Do not participate').click();

    cy.wait('@delete');
  });
});

describe('Testing session component as admin', () => {
  beforeEach(() => {
    cy.visit('/login');

    cy.intercept('POST', '/api/auth/login', {
      body: {
        id: 1,
        username: 'userName',
        firstName: 'firstName',
        lastName: 'lastName',
        admin: true,
      },
    });

    cy.intercept(
      {
        method: 'GET',
        url: '/api/session',
      },
      [
        {
          id: 1,
          name: 'Session 1',
          description: 'Description 1',
          date: '2024-06-10',
          teacher_id: 1,
          users: [1, 2],
          createdAt: '2024-06-10',
          updatedAt: '2024-06-10',
        },
        {
          id: 2,
          name: 'Session 2',
          description: 'Description 2',
          date: '2024-06-10',
          teacher_id: 2,
          users: [2, 3],
          createdAt: '2024-06-10',
          updatedAt: '2024-06-10',
        },
      ]
    );

    cy.get('input[formControlName=email]').type('test@mail.com');
    cy.get('input[formControlName=password]').type(
      `${'test123'}{enter}{enter}`
    );
  });

  it('See all sessions as admin', () => {
    // See correctly sessions list
    cy.get('mat-card-title').should('contain.text', 'Sessions');
    cy.get('mat-card-title').should('contain.text', 'Session 1');
    cy.get('mat-card-title').should('contain.text', 'Session 2');

    // See correctly buttons related to an admin use
    cy.get('button').contains('Edit').should('exist');
    cy.get('button').contains('Create').should('exist');
  });

  it('should see a session details', () => {
    cy.intercept('GET', '/api/session/1', {
      id: 1,
      name: 'Session 1',
      description: 'Description 1',
      date: '2024-06-12',
      teacher_id: 1,
      users: [1, 2, 3, 4],
      createdAt: '2024-06-11',
      updatedAt: '2024-06-10',
    });

    cy.intercept('GET', '/api/teacher/1', {
      id: 1,
      lastName: 'teacherLastName',
      firstName: 'teacherFirstName',
      createdAt: '2024-06-10',
      updatedAt: '2024-06-10',
    });

    cy.get('button').contains('Detail').click();

    // See correctly session details
    cy.get('h1').should('contain.text', 'Session 1');
    cy.get('.description').should('contain.text', 'Description 1');
    cy.get('.ml1').should('contain.text', '4 attendees');
    cy.get('.ml1').should('contain.text', 'June 12, 2024');
    cy.get('.created').should('contain.text', 'June 11, 2024');
    cy.get('.updated').should('contain.text', 'June 10, 2024');
    cy.get('.ml1').should('contain.text', 'teacherFirstName TEACHERLASTNAME');

    // See correctly delete button instead of participate button
    cy.get('button').contains('Delete').should('exist');
  });

  it('should edit a session', () => {
    cy.intercept('GET', '/api/session/1', {
      id: 1,
      name: 'Session 1',
      description: 'Description 1',
      date: '2024-06-12',
      teacher_id: 1,
      users: [1, 2, 3, 4, 6],
      createdAt: '2024-06-11',
      updatedAt: '2024-06-10',
    });

    cy.intercept('GET', '/api/teacher', [
      {
        id: 1,
        lastName: 'teacherLastName',
        firstName: 'teacherFirstName',
        createdAt: '2024-06-10',
        updatedAt: '2024-06-10',
      },
    ]);

    cy.intercept('PUT', '/api/session/1', {
      id: 1,
      name: 'Session 1 updated',
      description: 'Description 1',
      date: '2024-06-12',
      teacher_id: 1,
      users: [1, 2, 3, 4],
      createdAt: '2024-06-11',
      updatedAt: '2024-06-10',
    }).as('put');

    cy.get('button').contains('Edit').click();

    // See correctly session details
    cy.get('input[formControlName="name"]').should('have.value', 'Session 1');
    cy.get('input[formControlName="date"]').should('have.value', '2024-06-12');
    cy.get('mat-select[formControlName="teacher_id"]').should(
      'contain.text',
      'teacherFirstName teacherLastName'
    );
    cy.get('textarea[formControlName="description"]').should(
      'have.value',
      'Description 1'
    );

    // Change session details
    cy.get('input[formControlName=name]').clear().type('Session 1 updated');

    cy.get('button').contains('Save').click();

    cy.wait('@put');
  });
});

describe('Testing me component', () => {
  beforeEach(() => {
    cy.visit('/login');

    cy.intercept('POST', '/api/auth/login', {
      body: {
        id: 2,
        username: 'userName',
        firstName: 'firstName',
        lastName: 'lastName',
        admin: false,
      },
    });

    cy.intercept(
      {
        method: 'GET',
        url: '/api/session',
      },
      []
    ).as('session');

    cy.get('input[formControlName=email]').type('test@mail.com');
    cy.get('input[formControlName=password]').type(
      `${'test123'}{enter}{enter}`
    );

    cy.intercept('GET', '/api/user/2', {
      body: {
        id: 2,
        email: 'test@mail.com',
        firstName: 'firstName',
        lastName: 'lastName',
        admin: false,
        createdAt: '2024-06-10',
        updatedAt: '2024-06-10',
      },
    });

    cy.intercept(
      {
        method: 'GET',
        url: '/api/session',
      },
      []
    ).as('session');
  });

  it('should display  the user informations', () => {
    cy.contains('Account').click();
    cy.url().should('include', '/me');
    cy.get('p:contains("Name:")').should('contain', 'firstName LASTNAME');
    cy.get('p:contains("Email:")').should('contain', 'test@mail.com');
    cy.get('button:contains("Delete")').should('exist');
  });

  it('should delete the user', () => {
    cy.intercept('DELETE', '/api/user/2', {
      body: {
        id: 2,
        email: 'test@mail.com',
        firstName: 'firstName',
        lastName: 'lastName',
        admin: false,
        createdAt: '2024-06-10',
        updatedAt: '2024-06-10',
      },
    }).as('deleteUser');

    cy.contains('Account').click();
    cy.url().should('include', '/me');
    cy.get('button:contains("Delete")').click();
    // check if the delete message is displayed
    cy.get('.mat-simple-snack-bar-content').should('exist');
    cy.wait('@deleteUser');
    cy.url().should('include', '/');
  });

  it('should allow user to go back', () => {
    cy.contains('Account').click();
    cy.url().should('include', '/me');
    cy.get('button').contains('mat-icon', 'arrow_back').click();
    cy.url().should('include', '/sessions');
    cy.url().should('not.include', '/me');
  });
});

describe('Testing not-found component', () => {
  it('should display the not found page', () => {
    cy.visit('/404');
    cy.get('h1').should('contain.text', 'Page not found !');
  });
});
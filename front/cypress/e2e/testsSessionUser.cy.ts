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
      users: [1, 2 , 3 ,4],
      createdAt: '2024-06-11',
      updatedAt: '2024-06-10',
    },);

    cy.intercept('GET', '/api/teacher/1', {
        id: 1,
        lastName: 'teacherLastName',
        firstName: 'teacherFirstName',
        createdAt: '2024-06-10',
        updatedAt: '2024-06-10',
    })

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
      users: [1, 2 , 3 , 4, 6],
      createdAt: '2024-06-11',
      updatedAt: '2024-06-10',
    },);

    cy.intercept('GET', '/api/teacher/1', {
        id: 1,
        lastName: 'teacherLastName',
        firstName: 'teacherFirstName',
        createdAt: '2024-06-10',
        updatedAt: '2024-06-10',
    })

    cy.intercept('DELETE', '/api/session/1/participate/6', {
        id: 1,
        name: 'Session 1',
        description: 'Description 1',
        date: '2024-06-12',
        teacher_id: 1,
        users: [1, 2 , 3 ,4],
        createdAt: '2024-06-11',
        updatedAt: '2024-06-10',
        },).as('delete');
    

    cy.get('button').contains('Detail').click();

    cy.get('button').contains('Do not participate').should('exist');

    cy.get('button').contains('Do not participate').click();

    cy.wait('@delete');

  });   

});

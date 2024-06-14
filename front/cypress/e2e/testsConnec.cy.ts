describe('Testing register component', () => {
  it('Register successfull', () => {
    cy.visit('/register')

    cy.intercept('POST', '/api/auth/register', {
      body: {
        email: 'test@mail.com',
        firstName: 'firstName',
        lastName: 'lastName',
        password: 'password'
      },
    })


    cy.intercept(
      {
        method: 'GET',
        url: '/api/session',
      },
      []).as('session')

    cy.get('input[formControlName=email]').type("test@mail.com")
    cy.get('input[formControlName=firstName]').type("firstName")
    cy.get('input[formControlName=lastName]').type("lastName")
    cy.get('input[formControlName=password]').type(`${"password"}{enter}{enter}`)

    cy.url().should('include', '/login')

  });

});


describe('Testing login component', () => {
  it('Login successfull', () => {
    cy.visit('/login')

    cy.intercept('POST', '/api/auth/login', {
      body: {
        id: 1,
        username: 'userName',
        firstName: 'firstName',
        lastName: 'lastName',
        admin: true
      },
    })

    cy.intercept(
      {
        method: 'GET',
        url: '/api/session',
      },
      []).as('session')

    cy.get('input[formControlName=email]').type("test@mail.com")
    cy.get('input[formControlName=password]').type(`${"test!1234"}{enter}{enter}`)

    cy.url().should('include', '/sessions')
  })
});

  describe('Testing me component', () => {
    beforeEach(() => {
      cy.visit('/login')

      cy.intercept('POST', '/api/auth/login', {
        body: {
          id: 2,
          username: 'userName',
          firstName: 'firstName',
          lastName: 'lastName',
          admin: false
        }
      })

      cy.intercept(
        {
          method: 'GET',
          url: '/api/session',
        },
        []).as('session')
  
      cy.get('input[formControlName=email]').type("test@mail.com")
      cy.get('input[formControlName=password]').type(`${"test123"}{enter}{enter}`)

      cy.intercept('GET', '/api/user/2', {
        body: {
          id: 2,
          email: 'test@mail.com',
          firstName: 'firstName',
          lastName: 'lastName',
          admin: false,
          createdAt: '2024-06-10',
          updatedAt: '2024-06-10'
        }
      })

      cy.intercept({
        method: 'GET',
        url: '/api/session',
      },
        []).as('session')

      })
    

    it('should display  the user informations', () => {
      cy.contains('Account').click();
      cy.url().should('include', '/me');
      cy.get('p:contains("Name:")').should('contain', 'firstName LASTNAME');
      cy.get('p:contains("Email:")').should('contain', 'test@mail.com');
      
  })
});